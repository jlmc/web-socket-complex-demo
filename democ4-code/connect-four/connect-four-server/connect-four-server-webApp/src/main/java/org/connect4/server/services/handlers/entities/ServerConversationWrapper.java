package org.connect4.server.services.handlers.entities;

import java.io.Serializable;
import java.util.UUID;

import org.connect4.entities.Conversation;
import org.connect4.entities.User;
import org.connect4.messages.PrivateChatMessage;

/**
 * The Class ServerConversationWrapper.
 */
public class ServerConversationWrapper implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The conversation. */
    private final Conversation conversation;

    /**
     * Instantiates a new server conversation wrapper.
     * @param conversation
     *            the conversation
     */
    public ServerConversationWrapper(final Conversation conversation) {
        if (conversation == null) {
            throw new IllegalArgumentException("Conversation can't be null");
        }

        if (conversation.getOwner() == null || conversation.getOwner() == null) {
            throw new IllegalArgumentException("Conversation must have a owner and a guest");
        }
        this.conversation = conversation;

    }

    /**
     * Generate key.
     * @param c
     *            the c
     * @return the string
     */
    public static String generateKey(final ServerConversationWrapper c) {
        return generateKey(c.conversation.getOwner(), c.conversation.getGuest());
    }

    /**
     * Generate a unique key.
     * @param a
     *            the a
     * @param b
     *            the b
     * @return the string
     */
    public static String generateKey(final User a, final User b) {
        if (a == null || b == null) {
            return null;
        }
        if (a.getUsername() == null || b.getUsername() == null) {
            return null;
        }
        if (a.getUsername().trim().isEmpty() || b.getUsername().trim().isEmpty()) {
            return null;
        }

        if (a.getUsername().trim().toLowerCase().compareTo(b.getUsername().trim().toLowerCase()) < 0) {
            return String.format("[%s]<->[%s]", a.getUsername(), b.getUsername());
        }
        return String.format("[%s]<->[%s]", b.getUsername(), a.getUsername());

    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.conversation == null) ? 0 : this.conversation.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServerConversationWrapper other = (ServerConversationWrapper) obj;
        if (this.conversation == null) {
            if (other.conversation != null) {
                return false;
            }
        } else if (!this.conversation.equals(other.conversation)) {
            return false;
        }
        return true;
    }

    /**
     * Key factory.
     * @return the string
     */
    public static synchronized String keyFactory() {
        return UUID.randomUUID().toString();
    }

    /**
     * Gets the target.
     * @param msg
     *            the msg
     * @return the target
     */
    public static User getTarget(final PrivateChatMessage msg) {
        final User sender = msg.getSender();

        final User owner = msg.getConversation().getOwner();
        final User guest = msg.getConversation().getGuest();
        if (sender.equals(owner)) {
            return guest;
        } else if (sender.equals(guest)) {
            return owner;
        }

        return null;
    }

}
