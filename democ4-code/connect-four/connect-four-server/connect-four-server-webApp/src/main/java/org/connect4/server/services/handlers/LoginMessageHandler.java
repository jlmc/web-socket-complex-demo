package org.connect4.server.services.handlers;

import javax.websocket.Session;

import org.connect4.entities.AuthenticationStatusCode;
import org.connect4.entities.User;
import org.connect4.messages.LoginRequestMessage;
import org.connect4.messages.LoginResponseMessage;
import org.connect4.messages.Message;
import org.connect4.server.helpers.SendMessagerHelper;
import org.connect4.server.services.SessionManager;
import org.connect4.server.services.UnhandledMessageException;
import org.connect4.server.services.exeptions.AuthenticationException;
import org.connect4.services.dal.ServicesFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class LoginMessageHandler.
 */
public class LoginMessageHandler extends AbstractConnectFourMessageHandler {

    /** The Constant LOGGER. */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginMessageHandler.class);

    /** The session manager. */
    private final SessionManager sessionManager;

    /**
     * The Constructor.
     * @param sessionManager2
     *            the session manager2
     */
    public LoginMessageHandler(final SessionManager sessionManager2) {
        super();
        this.sessionManager = sessionManager2;
    }

    /**
     * handle login Message prompt, verify the accuracy of the data request, sent to the same user the response
     * {@link LoginResponseMessage} of labor.
     * @param session
     *            the session that performe the request
     * @param message
     *            the message should be a instance od {@link LoginRequestMessage}
     * @param parameters
     *            the parameters
     * @throws UnhandledMessageException
     *             the unhandled message exception
     */
    @Override
    public void handleMessage(final Session session, final Message message, final String... parameters)
            throws UnhandledMessageException {

        try {

            final LoginRequestMessage request = (LoginRequestMessage) message;

            final User user = new User(1, request.getUsername(), "", request.getPassword());

            try {

                final User authenticateUser = ServicesFacade.login(user);

                session.getUserProperties().put("USER", authenticateUser);

                if (this.sessionManager != null) {
                    this.sessionManager.addSession(session, authenticateUser);
                }

                final LoginResponseMessage response = new LoginResponseMessage();
                response.setToken(session.getId());
                response.setStatus(AuthenticationStatusCode.OK);
                response.setUser(authenticateUser);

                SendMessagerHelper.sendMessageTo(session, response);

            } catch (final AuthenticationException e) {

                final LoginResponseMessage response = new LoginResponseMessage();
                response.setToken(session.getId());
                response.setStatus(e.getStatusCode());

                SendMessagerHelper.sendMessageTo(session, response);
            }

        } catch (final Exception e) {
            final LoginResponseMessage response = new LoginResponseMessage();
            response.setToken(session.getId());
            response.setStatus(AuthenticationStatusCode.INTERNAL_ERROR);

            SendMessagerHelper.sendMessageTo(session, response);

        }

    }

    /**
     * Sets the users registry.
     * @param usersRegistry
     *            the new users registry
     */

}
