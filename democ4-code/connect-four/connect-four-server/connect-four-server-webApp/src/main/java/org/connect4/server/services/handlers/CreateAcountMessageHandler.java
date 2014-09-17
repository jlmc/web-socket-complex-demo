package org.connect4.server.services.handlers;

import javax.websocket.Session;

import org.connect4.entities.User;
import org.connect4.messages.CreateAcountRequestMessage;
import org.connect4.messages.CreateAcountResponseMessage;
import org.connect4.messages.CreateAcountResult;
import org.connect4.messages.Message;
import org.connect4.server.helpers.SendMessagerHelper;
import org.connect4.server.services.SessionManager;
import org.connect4.server.services.UnhandledMessageException;
import org.connect4.services.dal.ServicesFacade;
import org.connect4.services.dal.services.CreateUserExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class CreateAcountMessageHandler.
 */
public class CreateAcountMessageHandler extends AbstractConnectFourMessageHandler {

    /** The Constant LOGGER. */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAcountMessageHandler.class);

    /** The session manager. */
    @SuppressWarnings("unused")
    private final SessionManager sessionManager;

    /**
     * The Constructor.
     * @param sessionManager
     *            the session manager
     */
    public CreateAcountMessageHandler(final SessionManager sessionManager) {
        super();
        this.sessionManager = sessionManager;
    }

    /**
     * this handler will delegate the create user work and wait for the result of that word
     * send on {@link CreateAcountResponseMessage} to the session that performed the request for creation.
     * @param session
     *            the session that performe the request
     * @param message
     *            the message should be a instance od {@link CreateAcountRequestMessage}
     * @param parameters
     *            the parameters
     * @throws UnhandledMessageException
     *             the unhandled message exception
     */
    @Override
    public void handleMessage(final Session session, final Message message, final String... parameters)
            throws UnhandledMessageException {

        CreateAcountResult createAcountResult = CreateAcountResult.OK;
        User user = null;

        try {

            final CreateAcountRequestMessage request = (CreateAcountRequestMessage) message;

            user = ServicesFacade.createUser(request.getUser());
            createAcountResult = CreateAcountResult.OK;

        } catch (final CreateUserExeption e) {
            createAcountResult = e.getStatusCode();

        } catch (final Exception e) {
            createAcountResult = CreateAcountResult.INTERNAL_ERROR;
        }

        final CreateAcountResponseMessage response = new CreateAcountResponseMessage();
        response.setStatus(createAcountResult);
        response.setToken(session.getId());
        response.setUser(user);

        SendMessagerHelper.sendMessageTo(session, response);

    }
}
