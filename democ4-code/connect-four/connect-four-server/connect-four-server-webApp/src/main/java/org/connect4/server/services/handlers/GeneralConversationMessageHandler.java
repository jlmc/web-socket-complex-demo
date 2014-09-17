package org.connect4.server.services.handlers;

import java.util.Collection;

import javax.websocket.Session;

import org.connect4.messages.ChatMessage;
import org.connect4.messages.Message;
import org.connect4.server.services.SessionManager;
import org.connect4.server.helpers.SendMessagerHelper;
import org.connect4.server.services.UnhandledMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GeneralConversationMessageHandler.
 */
public class GeneralConversationMessageHandler extends AbstractConnectFourMessageHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneralConversationMessageHandler.class);

    /** The session manage. */
    private final SessionManager sessionManage;

    /**
     * The Constructor.
     * @param sessionManager
     *            the session manager
     */
    public GeneralConversationMessageHandler(final SessionManager sessionManager) {
        super();
        this.sessionManage = sessionManager;
    }

    /**
     * handler handle messages of general chat, sends the incoming message {@link ChatMessage} to all authenticated users online.
     * @param session
     *            the session that performe the request
     * @param message
     *            the message should be a instance od {@link ChatMessage}
     * @param parameters
     *            the parameters
     * @throws UnhandledMessageException
     *             the unhandled message exception
     */
    @Override
    public void handleMessage(final Session session, final Message message, final String... parameters)
            throws UnhandledMessageException {
        final ChatMessage response = (ChatMessage) message;

        if (response != null) {

            final String username = (response.getSender() != null) ? response.getSender().getUsername() : "";
            LOGGER.debug("Chat Message: {} - {} ", username, response.getMessage());

            final Collection<Session> sessions = this.sessionManage.getSessions();

            SendMessagerHelper.sendMessageTo(sessions, response);
        }

    }

}
