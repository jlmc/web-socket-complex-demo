package org.connect4.client.desktop.session.manager;

import org.connect4.entities.User;

/**
 * The Interface DesktopSession.
 */
public interface DesktopSession {

    /** The Constant USER. */
    public static final String USER = "user";

    /**
     * Set the.
     * @param key
     *            the key
     * @param object
     *            the object
     * @return true, if successful
     */
    boolean set(String key, Object object);

    /**
     * Removes the.
     * @param key
     *            the key
     * @return true, if successful
     */
    boolean remove(String key);

    /**
     * Gets the user.
     * @return the user
     */
    User getUser();

    /**
     * Sets the user.
     * @param user
     *            the new user
     */
    void setUser(User user);

    /**
     * Removes the user.
     * @return true, if successful
     */
    boolean removeUser();

    /**
     * Clean.
     */
    void clean();
}
