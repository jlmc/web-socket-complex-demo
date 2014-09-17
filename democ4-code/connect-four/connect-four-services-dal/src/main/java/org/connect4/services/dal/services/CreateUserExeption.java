package org.connect4.services.dal.services;

import org.connect4.messages.CreateAcountResult;

/**
 * The Class CreateUserExeption.
 */
public class CreateUserExeption extends IllegalArgumentException {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The status code. */
    private CreateAcountResult statusCode;

    /**
     * The Constructor.
     * @param statusCode
     *            the status code
     * @param message
     *            the message
     */
    public CreateUserExeption(final CreateAcountResult statusCode, final String message) {
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
    public CreateAcountResult getStatusCode() {
        return this.statusCode;
    }

    /**
     * Sets the status code.
     * @param statusCode
     *            the new status code
     */
    private void setStatusCode(final CreateAcountResult statusCode) {
        this.statusCode = statusCode;
    }

}
