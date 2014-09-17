package org.connect4.messages;

import org.connect4.entities.AuthenticationStatusCode;
import org.connect4.entities.User;

/**
 * The Class LoginResponseMessage.
 */
public class LoginResponseMessage extends Message {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new login response message.
     */
    public LoginResponseMessage() {
        super(MessageType.LOGIN_RESPONSE);
    }

    /** The status. */
    private AuthenticationStatusCode status;

    /** The user. */
    private User user;

    /**
     * Gets the status.
     * @return the status
     */
    public AuthenticationStatusCode getStatus() {
        return this.status;
    }

    /**
     * Sets the status.
     * @param status
     *            the new status
     */
    public void setStatus(final AuthenticationStatusCode status) {
        this.status = status;
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
