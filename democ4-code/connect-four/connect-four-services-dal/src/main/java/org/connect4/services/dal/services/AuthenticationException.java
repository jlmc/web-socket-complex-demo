package org.connect4.services.dal.services;

import org.connect4.entities.AuthenticationStatusCode;

/**
 * The Class AuthenticationException.
 */
public class AuthenticationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The status code. */
    private AuthenticationStatusCode statusCode;

    /**
     * Instantiates a new authentication exception.
     * @param statusCode
     *            the status code
     * @param message
     *            the message
     */
    public AuthenticationException(final AuthenticationStatusCode statusCode, final String message) {
        super(message);
        if (statusCode == null) {
            throw new IllegalArgumentException("statusCode can't be null");
        }
        setStatusCode(statusCode);
    }

    /**
     * Gets the status code.
     * @return the status code
     */
    public AuthenticationStatusCode getStatusCode() {
        return this.statusCode;
    }

    /**
     * Sets the status code.
     * @param statusCode
     *            the new status code
     */
    private void setStatusCode(final AuthenticationStatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
