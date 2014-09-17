package org.connect4.server.services;

import javax.websocket.Session;

import org.connect4.messages.Message;

/**
 * The Interface ConnectFourMessageHandler.
 */
public interface ConnectFourMessageHandler {

    /**
     * Handle message.
     * @param session
     *            the session
     * @param message
     *            the message
     * @param parameters
     *            the parameters
     * @throws UnhandledMessageException
     *             the unhandled message exception
     */
    void handleMessage(Session session, Message message, String... parameters) throws UnhandledMessageException;
}
