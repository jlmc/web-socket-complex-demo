/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.connect4.client.endpoint.clientendpoint;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
//import javax.websocket.MessageHandler;

import org.apache.log4j.Logger;
import org.connect4.client.endpoint.Notifier;
import org.connect4.decoders.JsonMessageDecoder;
import org.connect4.encoder.JsonMessageEncoder;
import org.connect4.messages.Message;

/**
 * The Class ConnectEndpoint.
 */
@ClientEndpoint(decoders = {JsonMessageDecoder.class }, encoders = {JsonMessageEncoder.class })
public class ConnectEndpoint implements Connector {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ConnectEndpoint.class);// LoggerFactory.getLogger(ConnectEndpoint.class);

    /** The user session. */
    private Session userSession;

    /** The rotter. */
    private Notifier rotter;

    /**
     * The Constructor.
     */
    public ConnectEndpoint() {
        super();
    }

    /**
     * Connect.
     * @param uri
     *            the uri
     * @param notifier
     *            the notifier
     * @throws DeploymentException
     *             the deployment exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Override
    public void connect(final URI uri, final Notifier notifier) throws DeploymentException, IOException {
        final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            LOGGER.info("trying to connect  with the server.");
            this.rotter = notifier;
            container.connectToServer(this, uri);

        } catch (DeploymentException | IOException e) {
            LOGGER.info(String.format("Connector cant link to the server cause by : { %s }", e.toString()));
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Desconnect.
     * @throws IOException
     */
    @Override
    public void desconnect() {
        try {
            this.userSession.close();
            this.userSession = null;
        } catch (final Exception e) {
            LOGGER.error("");
        }
    }

    /**
     * On open. Callback hook for connection open events
     * @param session
     *            the session which is opened
     */
    @OnOpen
    public void onOpen(final Session session) {
        LOGGER.info("connected with the server.");

        this.userSession = session;
    }

    /**
     * On message.
     * @param message
     *            the message
     */
    @OnMessage
    public void onMessage(final Message message) {
        LOGGER.info(String.format("Connector receve Message: %s", message.getType()));

        this.rotter.notify(message);
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.endpoint.clientendpoint.Connector#sendMessage(org.connect4.messages.Message)
     */
    @Override
    public void sendMessage(final Message msg) throws IOException, EncodeException {
        if (this.userSession != null) {
            try {
                this.userSession.getBasicRemote().sendObject(msg);

            } catch (IOException | EncodeException e) {
                throw e;
            }
        }
    }

    /**
     * On error.
     * @param t
     *            the t
     */
    @SuppressWarnings("static-method")
    @OnError
    public void onError(final Throwable t) {
        LOGGER.error("Errror caused by", t); // error("caused by: {}", t));
    }
}
