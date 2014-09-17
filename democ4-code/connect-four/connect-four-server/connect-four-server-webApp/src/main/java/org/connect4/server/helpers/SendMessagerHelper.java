package org.connect4.server.helpers;

import java.io.IOException;
import java.util.Collection;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.connect4.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SendMessagerHelper.
 */
public final class SendMessagerHelper {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMessagerHelper.class);

    /**
     * Instantiates a new send messager helper.
     */
    private SendMessagerHelper() {
        // nothing to do
    }

    /**
     * Helper method to send a message to a player. The optional extra parameters will be appended to the message, using spaces as separator.
     * @param message
     *            the message
     * @param sessions
     *            The web-socket sessions over which to send the message
     */
    public static void sendMessageTo(final Collection<Session> sessions, final Message message) {
        if (sessions != null && message != null) {
            for (final Session session : sessions) {
                sendMessageTo(session, message);
            }
        }
    }

    /**
     * Helper method to send a message to a player. The optional extra parameters will be appended to the message, using spaces as separator.
     * @param message
     *            The message to be send
     * @param session
     *            The web-socket session over which to send the message
     */
    public static void sendMessageTo(final Session session, final Message message) {
        if (session != null && session.isOpen() && message != null) {
            try {
                session.getBasicRemote().sendObject(message);
                LOGGER.info("sended message to session {} : {} ", session.getId(), message.getType());
            } catch (IOException | EncodeException e) {
                LOGGER.error("sending message to session {} caused by: {} ", session.getId(), e.getMessage());
            }
        }
    }
}
