package org.connect4.server.services.impl;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.Session;

import org.connect4.messages.Message;
import org.connect4.messages.MessageType;
import org.connect4.server.services.ConnectFourMessageHandler;
import org.connect4.server.services.MessageHandlerRegistry;
import org.connect4.server.services.NotificationEventManager;
import org.connect4.server.services.SessionManager;
import org.connect4.server.services.UnhandledMessageException;
import org.connect4.server.services.handlers.CreateAcountMessageHandler;
import org.connect4.server.services.handlers.GeneralConversationMessageHandler;
import org.connect4.server.services.handlers.LoginMessageHandler;
import org.connect4.server.services.handlers.OnLineUsersRequestHandler;
import org.connect4.server.services.handlers.PrivateConversationMessageHandler;

/**
 * The Class InMemoryMessageHandlerRegistry.
 * A message handler registry which stores the handlers in memory.
 */
@Named
@ApplicationScoped
public class InMemoryMessageHandlerRegistry implements MessageHandlerRegistry {

    /** The registry. */
    private final Map<MessageType, ConnectFourMessageHandler> registry;

    /**
     * Instantiates a new in memory message handler registry.
     * Constructor, initializing handler for {@link org.connect4.messages.MessageType}s.
     */
    public InMemoryMessageHandlerRegistry() {
        this.registry = new HashMap<>();

    }

    /**
     * Post construct.
     * @param sessionManager
     *            the session manager
     * @param notificationEventManager
     *            the notification event manager
     */
    @Inject
    @Named
    public void postConstruct(final SessionManager sessionManager,
            final NotificationEventManager notificationEventManager) {

        sessionManager.setNotificationEventManager(notificationEventManager);

        this.registry.put(MessageType.LOGIN_REQUEST, new LoginMessageHandler(sessionManager));
        this.registry.put(MessageType.PUBLIC, new GeneralConversationMessageHandler(sessionManager));
        this.registry.put(MessageType.PRIVATE, new PrivateConversationMessageHandler(sessionManager));
        this.registry.put(MessageType.CREATE_ACOUNT_REQUEST, new CreateAcountMessageHandler(sessionManager));
        this.registry.put(MessageType.ON_LINE_USERS_REQUEST, new OnLineUsersRequestHandler(sessionManager));

    }

    /**
     * Handle the give message by using a registered {@link Message}. If no such handler can be found an UnhandledMessageException is thrown.
     * @param session
     *            the main initiator of the message
     * @param message
     *            the message to handle
     * @param parameters
     *            the parameters, optional message parameters
     * @throws UnhandledMessageException
     *             when an appropriate handler can not be found, or if the handler itself throws this exception.
     */
    @Override
    public void handle(final Session session, final Message message, final String... parameters)
            throws UnhandledMessageException {
        final ConnectFourMessageHandler handler = this.registry.get(message.getType());
        if (handler != null) {
            handler.handleMessage(session, message, parameters);
        } else {
            throw new UnhandledMessageException(message, String.format("No HANDLER found for %s ", message.getType()
                    .toString()));
        }

    }

    /*
     * (non-Javadoc)
     * @see org.connect4.services.MessageHandlerRegistry#handle(javax.websocket.Session, org.connect4.messages.MessageType, java.lang.String[])
     */
    @Override
    public void handle(final Session session, final MessageType messageType, final String... parameters)
            throws UnhandledMessageException {
        final ConnectFourMessageHandler handler = this.registry.get(messageType);
        if (handler != null) {
            handler.handleMessage(session, null, new String[] {});
        } else {
            throw new UnhandledMessageException(null, String.format("No HANDLER found for %s ", messageType.toString()));
        }

    }

}
