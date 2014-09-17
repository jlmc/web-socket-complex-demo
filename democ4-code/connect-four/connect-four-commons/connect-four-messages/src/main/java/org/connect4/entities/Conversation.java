package org.connect4.entities;

import java.io.Serializable;

/**
 * The Class Conversation.
 */
public class Conversation implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * The id.
     * unique identifier of the conversation,
     * each conversation is identified with an identifier that is not repeated in any other conversation.
     */
    private String id;

    /**
     * The owner.
     * user making the call to start the conversation, this user owns the conversation.
     */
    private User owner;

    /** The guest. */
    private User guest;

    /** The state. */
    private ConversationState state;

    /**
     * Instantiates a new conversation.
     */
    public Conversation() {
        super();
        this.owner = null;
        this.guest = null;
        this.state = ConversationState.INDEFINED;
        setId(idFactory(this));
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
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
        final Conversation other = (Conversation) obj;
        if (this.id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Instantiates a new conversation. the state will be set with {@link ConversationState.WAITING} default
     * @param owner
     *            the owner - The owner. user making the call to start the conversation, this user owns the
     *            conversation.
     * @param guest
     *            the guest -user who is invited to participate.
     * @see ConversationState.WAITING
     */
    public Conversation(final User owner, final User guest) {
        super();
        this.owner = owner;
        this.guest = guest;
        this.state = ConversationState.WAITING;
        setId(idFactory(this));

    }

    /**
     * Instantiates a new conversation.
     * @param owner
     *            the owner - The owner. user making the call to start the conversation, this user owns the
     *            conversation.
     * @param guest
     *            the guest -user who is invited to participate.
     * @param state
     *            the state
     * @see ConversationState.WAITING
     */
    public Conversation(final User owner, final User guest, final ConversationState state) {
        super();

        this.owner = owner;
        this.guest = guest;
        this.state = state;
        setId(idFactory(this));
    }

    /**
     * Gets the id.
     * unique identifier of the conversation,
     * each conversation is identified with an identifier that is not repeated in any other conversation.
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id.
     * unique identifier of the conversation,
     * each conversation is identified with an identifier that is not repeated in any other conversation.
     * @param id
     *            the new id
     */
    private void setId(final String id) {
        this.id = id;
    }

    /**
     * Gets the owner.
     * user who made the call to start the conversation, this user owns the conversation.
     * @return the owner
     */
    public User getOwner() {
        return this.owner;
    }

    /**
     * Sets the owner.
     * user who made the call to start the conversation, this user owns the conversation.
     * @param owner
     *            the new owner
     */
    public void setOwner(final User owner) {
        this.owner = owner;
        setId(idFactory(this));
    }

    /**
     * Gets the guest.
     * user who is invited to participate.
     * @return the guest
     */
    public User getGuest() {
        return this.guest;
    }

    /**
     * Sets the guest.
     * user who is invited to participate.
     * @param guest
     *            the new guest
     */
    public void setGuest(final User guest) {
        this.guest = guest;
        setId(idFactory(this));

    }

    /**
     * Gets the state.
     * current state of the conversation.
     * @return the state
     * @see {@link ConversationState}
     */
    public ConversationState getState() {
        return this.state;
    }

    /**
     * Sets the state.
     * current state of the conversation.
     * @param state
     *            the new state
     */
    public void setState(final ConversationState state) {
        this.state = state;
    }

    /**
     * The Enum ConversationState.
     * Represents the current state of the conversation.
     */
    public enum ConversationState {

        /**
         * The not started.
         * Without any activity, are not yet defined the owner or the guest.
         */
        INDEFINED,
        /**
         * The Waiting State.
         * When the conversation is awaiting the response of the guest user, ie,
         * after the owner has made ​​one or more actions,
         * and while the guest does not do any action on this conversation.
         **/
        WAITING,

        /**
         * The Running State.
         * After both users (owner and guest) have done work on this conversation,
         * and while none of them end this conversation.
         */
        RUNNING,

        /**
         * The Closed State.
         * When one of the conversation participants ends it.
         */
        CLOSED;
    }

    /**
     * Id factory.
     * @param conversation
     *            the conversation
     * @return the string
     */
    public static String idFactory(final Conversation conversation) {
        if (conversation.guest == null || conversation.owner == null) {
            return null;
        } else if (conversation.guest.getUsername() == null || conversation.owner.getUsername() == null) {
            return null;
        } else if (conversation.guest.getUsername().trim().isEmpty()
                || conversation.owner.getUsername().trim().isEmpty()) {
            return null;
        }

        final String ownerUsername = conversation.getOwner().getUsername().trim().toLowerCase();
        final String guestUsername = conversation.getGuest().getUsername().trim().toLowerCase();

        if (ownerUsername.compareToIgnoreCase(guestUsername) > 0) {
            return String.format("[%s]<->[%s]", ownerUsername, guestUsername);
        }
        return String.format("[%s]<->[%s]", guestUsername, ownerUsername);
    }
}
