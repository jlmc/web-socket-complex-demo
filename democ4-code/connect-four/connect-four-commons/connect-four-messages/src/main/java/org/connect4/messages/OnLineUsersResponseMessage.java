package org.connect4.messages;

import java.util.List;

import org.connect4.entities.User;

/**
 * The Class OnLineUsersResponseMessage.
 */
public class OnLineUsersResponseMessage extends Message {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The on line users. */
    private List<User> onLineUsers;

    /**
     * Instantiates a new on line users response message.
     */
    public OnLineUsersResponseMessage() {
        super(MessageType.ON_LINE_USERS_RESPONSE);
    }

    /**
     * Gets the on line users.
     * @return the on line users
     */
    public List<User> getOnLineUsers() {
        return this.onLineUsers;
    }

    /**
     * Sets the on line users.
     * @param onlineUsers
     *            the new on line users
     */
    public void setOnLineUsers(final List<User> onlineUsers) {
        this.onLineUsers = onlineUsers;
    }

}
