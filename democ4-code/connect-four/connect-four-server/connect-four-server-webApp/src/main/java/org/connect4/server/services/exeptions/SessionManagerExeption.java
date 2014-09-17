package org.connect4.server.services.exeptions;

/**
 * The Class SessionManagerExeption.
 */
public class SessionManagerExeption extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * The Constructor.
     * @param message
     *            the message
     */
    public SessionManagerExeption(final String message) {
        super(message);
    }

}
