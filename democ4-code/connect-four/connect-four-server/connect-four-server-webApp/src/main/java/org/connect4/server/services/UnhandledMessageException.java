package org.connect4.server.services;

import org.connect4.messages.Message;

/**
 * The Class UnhandledMessageException.
 */
public class UnhandledMessageException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The message. */

    @SuppressWarnings("unused")
    private final Message message;

    /**
     * Constructor setting the {@link org.connect4.messages.Message} that could not be handled, with error message and root cause.
     * @param message
     *            the message
     * @param errorMessage
     *            the error message
     * @param cause
     *            the root cause
     */
    public UnhandledMessageException(final Message message, final String errorMessage, final Throwable cause) {
        super(errorMessage, cause);
        this.message = message;
    }

    /**
     * Constructor setting the {@link org.connect4.messages.Message} that could not be handled, with error message.
     * @param message
     *            the message
     * @param errorMessage
     *            the error message
     */
    public UnhandledMessageException(final Message message, final String errorMessage) {
        super(errorMessage);
        this.message = message;
    }
}
