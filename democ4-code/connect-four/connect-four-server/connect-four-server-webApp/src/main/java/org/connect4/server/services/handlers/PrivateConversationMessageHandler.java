package org.connect4.server.services.handlers;

import java.util.HashSet;

import javax.websocket.Session;

import org.connect4.entities.User;
import org.connect4.messages.Message;
import org.connect4.messages.PrivateChatMessage;
import org.connect4.server.helpers.SendMessagerHelper;
import org.connect4.server.services.SessionManager;
import org.connect4.server.services.UnhandledMessageException;
import org.connect4.server.services.handlers.entities.ServerConversationWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PrivateConversationMessageHandler.
 */
public class PrivateConversationMessageHandler extends AbstractConnectFourMessageHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateConversationMessageHandler.class);

    /** The session manager. */
    private final SessionManager sessionManager;

    /**
     * The Constructor.
     * @param sessionManager
     *            the session manager
     */
    public PrivateConversationMessageHandler(final SessionManager sessionManager) {
        super();
        this.sessionManager = sessionManager;
    }

    /**
     * handle message of private conversation.
     * @param session
     *            the session
     * @param message
     *            the message
     * @param parameters
     *            the parameters
     * @throws UnhandledMessageException
     *             the unhandled message exception
     */
    @Override
    public void handleMessage(final Session session, final Message message, final String... parameters)
            throws UnhandledMessageException {

        try {
            final PrivateChatMessage requestChatMessage = (PrivateChatMessage) message;

            final User sender = requestChatMessage.getSender();
            final User target = ServerConversationWrapper.getTarget(requestChatMessage);

            if (sender != null && target != null) {
                final HashSet<Session> sessions = new HashSet<>();
                sessions.addAll(this.sessionManager.getSessionOf(sender));
                sessions.addAll(this.sessionManager.getSessionOf(target));

                SendMessagerHelper.sendMessageTo(sessions, requestChatMessage);
            }
        } catch (final Exception e) {
            LOGGER.error("Problems handler the privates Mensages: {}", e.getMessage());
        }

    }
}
