package org.connect4.client.endpoint.clientendpoint;

import java.io.IOException;
import java.net.URI;

import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;

import org.connect4.client.endpoint.Notifier;
import org.connect4.messages.Message;

/**
 * The Interface Connector.
 */
public interface Connector {

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
    void connect(URI uri, Notifier notifier) throws DeploymentException, IOException;

    /**
     * Send message.
     * @param message
     *            the message
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws EncodeException
     *             the encode exception
     */
    void sendMessage(Message message) throws IOException, EncodeException;

    /**
     * Desconnect.
     */
    void desconnect();
}
