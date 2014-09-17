package org.connect4.client.desktop.controllers.services;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import org.connect4.client.desktop.models.UserWrapper;
import org.connect4.client.endpoint.NotificableService;
import org.connect4.client.endpoint.Notifier;
import org.connect4.client.endpoint.Router;
import org.connect4.entities.Conversation;
import org.connect4.entities.User;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;
import org.connect4.messages.PrivateChatMessage;

/**
 * The Class PrivateMessagesService.
 */
public class PrivateMessagesService implements NotificableService {

    /** The notifier. */
    private final Notifier notifier;

    /** The users. */
    private final ObservableList<UserWrapper> users;

    /** The session user. */
    private final User sessionUser;

    /**
     * Instantiates a new private messages service.
     * @param users
     *            the users
     * @param notifier
     *            the notifier
     * @param sessionUser
     *            the session user
     */
    public PrivateMessagesService(final ObservableList<UserWrapper> users, final Notifier notifier,
            final User sessionUser) {
        super();
        this.users = users;
        this.notifier = Router.getInstance();
        this.sessionUser = sessionUser;
    }

    /**
     * Start.
     */
    @Override
    public void start() {
        this.notifier.subscribe(this, MessageType.PRIVATE);
    }

    /**
     * Stop.
     */
    @Override
    public void stop() {
        this.notifier.unSubscribe(this, MessageType.PRIVATE);
    }

    /**
     * Update.
     * @param message
     *            the message
     */
    @Override
    public void update(final Message message) {
        switch (message.getType()) {
        case PRIVATE:
            final PrivateChatMessage chatMessage = (PrivateChatMessage) message;

            final Conversation c = chatMessage.getConversation();

            final User u = getOtherUser(this.sessionUser, c);
            if (u == null) {
                return;
            }

            UserWrapper uw = new UserWrapper(u);
            if (this.users.contains(uw)) {
                uw = this.users.get(this.users.indexOf(uw));
                if (uw != null) {

                    final User sender = chatMessage.getSender();
                    if (uw.getConversation() == null) {
                        uw.setConversation(new Conversation(sender, this.sessionUser));
                    }
                    uw.appendMessage(sender, chatMessage.getMessage());

                    final UserWrapper yu = uw;
                    Platform.runLater(new Runnable() {

                        @SuppressWarnings("synthetic-access")
                        @Override
                        public void run() {
                            PrivateMessagesService.this.users.remove(yu);
                            PrivateMessagesService.this.users.add(0, yu);

                        }
                    });

                }

            }

            break;
        default:
            break;
        }

    }

    /**
     * Gets the other user.
     * @param user
     *            the user
     * @param c
     *            the c
     * @return the other user
     */
    public static User getOtherUser(final User user, final Conversation c) {
        if (user != null && c != null && c.getOwner() != null && c.getGuest() != null) {
            if (user.equals(c.getOwner())) {
                return c.getGuest();
            } else if (user.equals(c.getGuest())) {
                return c.getOwner();
            }
        }
        return null;
    }
}
