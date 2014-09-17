package org.connect4.messages;

import org.connect4.entities.User;

/**
 * The Class ChatMessage.
 */
public class ChatMessage extends Message {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The message. */
    private String message;

    /** The sender. */
    private User sender;

    /**
     * The Constructor.
     */
    public ChatMessage() {
        super(MessageType.PUBLIC);
    }

    /**
     * Gets the message.
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message.
     * @param message
     *            the message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Gets the sender.
     * @return the sender
     */
    public User getSender() {
        return this.sender;
    }

    /**
     * Sets the sender.
     * @param sender
     *            the new sender
     */
    public void setSender(final User sender) {
        this.sender = sender;
    }

}
