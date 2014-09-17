package org.connect4.server.services.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.websocket.Session;

import org.connect4.entities.User;
import org.connect4.messages.Message;
import org.connect4.messages.OnLineUsersRequestMessage;
import org.connect4.messages.OnLineUsersResponseMessage;
import org.connect4.server.helpers.SendMessagerHelper;
import org.connect4.server.services.SessionManager;
import org.connect4.server.services.UnhandledMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class OnLineUsersRequestHandler.
 */
public class OnLineUsersRequestHandler extends AbstractConnectFourMessageHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(OnLineUsersRequestHandler.class);

    /** The session manager. */
    private final SessionManager sessionManager;

    /**
     * The Constructor.
     * @param sessionManager
     *            the session manager
     */
    public OnLineUsersRequestHandler(final SessionManager sessionManager) {
        super();
        this.sessionManager = sessionManager;
    }

    /**
     * handle OnLineUsersRequestMessage prompt, order to know which authenticated users that are connected,
     * sent to the same user the response {@link OnLineUsersRequestMessage} of labor.
     * @param session
     *            the session that performe the request
     * @param message
     *            the message should be a instance od {@link OnLineUsersResponseMessage}
     * @param parameters
     *            the parameters
     * @throws UnhandledMessageException
     *             the unhandled message exception
     */
    @Override
    public void handleMessage(final Session session, final Message message, final String... parameters) throws UnhandledMessageException {

        final OnLineUsersRequestMessage request = (OnLineUsersRequestMessage) message;

        final List<User> users = new ArrayList<>();
        final OnLineUsersResponseMessage response = new OnLineUsersResponseMessage();
        response.setToken(request.getToken());
        response.setOnLineUsers(users);

        if (this.sessionManager != null) {
            final Collection<User> onlineUsers = this.sessionManager.getUsers();
            users.addAll(onlineUsers);

            LOGGER.debug("send All Online users.");
            SendMessagerHelper.sendMessageTo(session, response);

        }
    }
}
