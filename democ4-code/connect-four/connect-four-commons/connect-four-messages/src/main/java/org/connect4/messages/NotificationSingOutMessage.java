package org.connect4.messages;

import org.connect4.entities.User;

/**
 * The Class NotificationSingOutMessage.
 */
public class NotificationSingOutMessage extends Message {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The user. */
    private User user;

    /**
     * Instantiates a new notification sing out message.
     */
    public NotificationSingOutMessage() {
        super(MessageType.NOTIFICATION_SIGN_OUT);
    }

    /**
     * Gets the user.
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Sets the user.
     * @param user
     *            the new user
     */
    public void setUser(final User user) {
        this.user = user;
    }

}
