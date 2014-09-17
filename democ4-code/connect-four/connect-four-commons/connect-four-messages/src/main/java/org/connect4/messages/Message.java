package org.connect4.messages;

import java.io.Serializable;
import java.security.InvalidParameterException;

/**
 * The Class Message.
 */
public abstract class Message implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The type. */
    private MessageType type;

    /** The token. */
    private String token;

    /**
     * The Constructor.
     */
    public Message() {
        this(MessageType.PUBLIC);
    }

    /**
     * The Constructor.
     * @param type
     *            the type
     */
    public Message(final MessageType type) {
        super();

        if (type == null) {
            throw new InvalidParameterException("the type can't be null");
        }

        setType(type);
    }

    /**
     * Gets the type.
     * @return the type
     */
    public MessageType getType() {
        return this.type;
    }

    /**
     * Sets the type.
     * @param type
     *            the type
     */
    protected void setType(final MessageType type) {
        this.type = type;
    }

    /**
     * Gets the token.
     * @return the token
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Sets the token.
     * @param token
     *            the new token
     */
    public void setToken(final String token) {
        this.token = token;
    }

}
