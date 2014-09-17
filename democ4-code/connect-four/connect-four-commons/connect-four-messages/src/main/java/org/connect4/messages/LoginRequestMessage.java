package org.connect4.messages;


/**
 * The Class LoginRequestMessage.
 */
public class LoginRequestMessage extends Message {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /**
     * Instantiates a new login request message.
     */
    public LoginRequestMessage() {
        super(MessageType.LOGIN_REQUEST);
    }

    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username.
     * @param username
     *            the new username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     * @param password
     *            the new password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

}
