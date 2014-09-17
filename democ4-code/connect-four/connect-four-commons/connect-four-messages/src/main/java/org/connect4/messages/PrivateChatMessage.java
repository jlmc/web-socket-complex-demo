package org.connect4.messages;

import org.connect4.entities.Conversation;
import org.connect4.entities.User;

/**
 * The Class PrivateChatMessage.
 */
public class PrivateChatMessage extends Message {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The conversation. */
    private Conversation conversation;

    /** The message. */
    private String message;

    /** The sender. */
    private User sender;

    /**
     * The Constructor.
     */
    public PrivateChatMessage() {
        super(MessageType.PRIVATE);
    }

    /**
     * Gets the conversation.
     * @return the conversation
     */
    public Conversation getConversation() {
        return this.conversation;
    }

    /**
     * Gets the message.
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Gets the sender.
     * @return the sender
     */
    public User getSender() {
        return this.sender;
    }

    /**
     * Sets the conversation.
     * @param conversation
     *            the new conversation
     */
    public void setConversation(final Conversation conversation) {
        this.conversation = conversation;
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
     * Sets the sender.
     * @param sender
     *            the new sender
     */
    public void setSender(final User sender) {
        this.sender = sender;
    }

}
