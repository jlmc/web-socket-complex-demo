package org.connect4;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.connect4.decoders.JsonMessageDecoder;
import org.connect4.encoder.JsonMessageEncoder;
import org.connect4.messages.Message;
import org.connect4.server.services.MessageHandlerRegistry;
import org.connect4.server.services.NotificationEventManager;
import org.connect4.server.services.SessionManager;
import org.connect4.server.services.UnhandledMessageException;
import org.connect4.server.services.exeptions.SessionManagerExeption;
import org.connect4.utils.functions.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ConnectFourEndpoint.
 */
@ServerEndpoint(value = "/endpoint", decoders = {JsonMessageDecoder.class }, encoders = {JsonMessageEncoder.class })
public class ConnectFourEndpoint {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectFourEndpoint.class);

    /** The handler registry. */
    private final MessageHandlerRegistry handlerRegistry;

    /** The session manager. */
    private final SessionManager sessionManager;

    /** The notification event manager. */
    @SuppressWarnings("unused")
    private final NotificationEventManager notificationEventManager;

    /**
     * Instantiates a new connect four endpoint.
     * @param handlerRegistry
     *            the handler registry
     * @param notificationEventManager
     *            the notification event manager
     * @param sessionManager
     *            the session manager
     */
    @Inject
    @Named
    public ConnectFourEndpoint(final MessageHandlerRegistry handlerRegistry,
            final NotificationEventManager notificationEventManager, final SessionManager sessionManager) {
        this.notificationEventManager = notificationEventManager;
        this.handlerRegistry = handlerRegistry;
        this.sessionManager = sessionManager;
    }

    /**
     * On close.
     * @param session
     *            the session
     * @throws UnhandledMessageException
     *             the unhandled message exception
     */
    @OnClose
    public void onClose(final Session session) throws UnhandledMessageException {
        LOGGER.info("closing the Session {}", session.getId());

        if (this.sessionManager != null) {
            try {
                this.sessionManager.removeSession(session);
            } catch (final SessionManagerExeption e) {
                LOGGER.warn("on close problem cause by: {}", e.getMessage());
            }
        }

    }

    /**
     * On error.
     * @param session
     *            the session
     * @param t
     *            the t
     */
    @OnError
    public void onError(final Session session, final Throwable t) {
        LOGGER.info("closing the Session {}", session.getId());

        if (this.sessionManager != null) {
            try {
                this.sessionManager.removeSession(session);
            } catch (final SessionManagerExeption e) {
                LOGGER.warn("on close problem cause by: {}", e.getMessage());
            }
        }
    }

    /**
     * Called when a web-socket message arrives.
     * This method will handle valid incoming messages through the MessageHandlerRegistry.
     * If the message is invalid an exception will be thrown.
     * Invalid messages are messages that are not recognized by the system,
     * messages with bad parameters or messages that shouldn't have been send by a client in the first place.
     * @param session
     *            the session
     * @param message
     *            the message
     * @throws UnhandledMessageException
     *             the unhandled message exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @OnMessage
    public void onMessage(final Session session, final Message message) throws UnhandledMessageException, IOException {
        LOGGER.info("on Message :  {}", message.getType());
        try {
            Preconditions.checkNotNull(message, "message can't be null.");

            this.handlerRegistry.handle(session, message, new String[] {});

        } catch (final UnhandledMessageException e) {
            LOGGER.error("problem onMessage: {}", e.getMessage());

        }
    }

    /**
     * On open.
     * @param session
     *            the session
     * @throws UnhandledMessageException
     *             the unhandled message exception
     */
    @SuppressWarnings("static-method")
    @OnOpen
    public void onOpen(final Session session) throws UnhandledMessageException {
        LOGGER.info("Open connection to: {}", session.getId());

    }

}
