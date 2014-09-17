package org.connect4.server.services;

import javax.websocket.Session;

import org.connect4.messages.Message;
import org.connect4.messages.MessageType;

/**
 * The Interface MessageHandlerRegistry.
 */
public interface MessageHandlerRegistry {

    /**
     * Handle the give message by using a registered {@link ConnectFourMessageHandler}. If no such handler can be found an UnhandledMessageException is thrown.
     * @param session
     *            the main initiator of the message
     * @param message
     *            the message to handle
     * @param parameters
     *            the parameters, optional message parameters
     * @throws UnhandledMessageException
     *             when an appropriate handler can not be found, or if the handler itself throws this exception.
     */
    void handle(Session session, Message message, String... parameters) throws UnhandledMessageException;

    /**
     * Handle the give message by using a registered {@link ConnectFourMessageHandler}. If no such handler can be found an UnhandledMessageException is thrown.
     * @param session
     *            the main initiator of the message
     * @param messageType
     *            the message type
     * @param parameters
     *            the parameters, optional message parameters
     * @throws UnhandledMessageException
     *             the unhandled message exception
     */
    void handle(Session session, MessageType messageType, String... parameters) throws UnhandledMessageException;

}
