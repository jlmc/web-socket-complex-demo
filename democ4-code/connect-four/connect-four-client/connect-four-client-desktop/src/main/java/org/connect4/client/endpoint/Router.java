/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.connect4.client.endpoint;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.DeploymentException;

import org.apache.log4j.Logger;
import org.connect4.client.endpoint.clientendpoint.Connector;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;

/**
 * The Class Rotter.
 */
public final class Router implements Notifier {

    /** The instance. */
    private static Router instance;

    /** The Constant INSTANCE_BLOCK. */
    private static final Object INSTANCE_BLOCK = new Object();

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(Router.class);
    // LoggerFactory.getLogger(Router.class);

    /** The notificables. */
    private final Map<MessageType, Set<Notificable>> notificables;

    /** The connect endpoint. */
    private Connector connector;

    /**
     * Instantiates a new rotter.
     */
    private Router() {
        super();

        this.notificables = Collections.synchronizedMap(new HashMap<MessageType, Set<Notificable>>());

        this.notificables.put(MessageType.LOGIN_RESPONSE, new HashSet<Notificable>());
        this.notificables.put(MessageType.PUBLIC, new HashSet<Notificable>());
        this.notificables.put(MessageType.PRIVATE, new HashSet<Notificable>());
        this.notificables.put(MessageType.CREATE_ACOUNT_RESPONSE, new HashSet<Notificable>());
        this.notificables.put(MessageType.ON_LINE_USERS_RESPONSE, new HashSet<Notificable>());
        this.notificables.put(MessageType.NOTIFICATION_SIGN_IN, new HashSet<Notificable>());
        this.notificables.put(MessageType.NOTIFICATION_SIGN_OUT, new HashSet<Notificable>());

    }

    /**
     * Gets the single instance of Rotter.
     * @return single instance of Rotter
     */
    public static Router getInstance() {
        synchronized (INSTANCE_BLOCK) {
            if (instance == null) {
                instance = new Router();
            }
        }
        return instance;
    }

    /**
     * Subscribe.
     * @param notifiable
     *            the notifiable
     * @param messageTypes
     *            the message types
     */
    @Override
    public void subscribe(final Notificable notifiable, final MessageType... messageTypes) {
        if (notifiable != null && messageTypes != null && messageTypes.length > 0) {
            for (final MessageType messageType : messageTypes) {
                if (this.notificables.containsKey(messageType)) {
                    this.notificables.get(messageType).add(notifiable);
                }
            }
        }
    }

    /**
     * Un subscribe.
     * @param notifiable
     *            the notifiable
     * @param messageTypes
     *            the message types
     */
    @Override
    public void unSubscribe(final Notificable notifiable, final MessageType... messageTypes) {
        if (notifiable != null && messageTypes != null && messageTypes.length > 0) {
            for (final MessageType messageType : messageTypes) {
                if (this.notificables.containsKey(messageType)) {
                    this.notificables.get(messageType).remove(notifiable);
                }
            }
        }
    }

    /**
     * Notify.
     * @param message
     *            the message
     */
    @Override
    public void notify(final Message message) {
        if (message != null && message.getType() != null) {

            if (this.notificables.containsKey(message.getType())) {
                final Set<Notificable> objectToNotify = this.notificables.get(message.getType());

                if (objectToNotify != null) {
                    for (final Notificable notificable : objectToNotify) {
                        if (notificable != null) {
                            notificable.update(message);
                        }
                    }

                }
            }
        }
    }

    /**
     * Send message, using the connector.
     * @param message
     *            the message
     * @return true if the message was sent by the connector without
     *         problems, otherwise returns false.
     */
    @Override
    public boolean sendMessage(final Message message) {
        if (message != null) {
            if (this.connector != null) {

                try {
                    this.connector.sendMessage(message);
                    LOGGER.debug("Message sended " + message.getType());
                    return true;
                } catch (final Exception e) {
                    LOGGER.debug("Rotther can't send the message caused by: " + e.toString());
                    return false;
                }
            }
            throw new RuntimeException("this connect Endpoint is null");
        }
        LOGGER.debug("Rotter don't send null values. message is null");
        return false;
    }

    /**
     * Inits the.
     * @param connect
     *            the connect
     */
    public void init(final Connector connect) {
        this.connector = connect;

    }

    /**
     * Connect.
     * @param uri
     *            the uri
     * @throws DeploymentException
     *             , IOException
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Override
    public void connect(final URI uri) throws DeploymentException, IOException {
        try {
            this.connector.connect(uri, this);
        } catch (DeploymentException | IOException e) {
            LOGGER.error("connector can't connect. " + e.getMessage());
            throw e;
        }
    }

    /**
     * Disconnect.
     */
    @Override
    public void disconnect() {
        this.connector.desconnect();
    }

}
