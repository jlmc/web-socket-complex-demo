package org.connect4.messages;

import org.connect4.entities.User;

/**
 * The Class CreateAcountResponseMessage.
 */
public class CreateAcountResponseMessage extends Message {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The status. */
    private CreateAcountResult status;

    /** The user. */
    private User user;

    /**
     * Instantiates a new creates the acount response message.
     */
    public CreateAcountResponseMessage() {
        super(MessageType.CREATE_ACOUNT_RESPONSE);
    }

    /**
     * Gets the status.
     * @return the status
     */
    public CreateAcountResult getStatus() {
        return this.status;
    }

    /**
     * Sets the status.
     * @param status
     *            the new status
     */
    public void setStatus(final CreateAcountResult status) {
        this.status = status;
    }

    /**
     * Sets the user.
     * @param user
     *            the user
     */
    public void setUser(final User user) {
        this.user = user;

    }

    /**
     * Gets the user.
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

}
