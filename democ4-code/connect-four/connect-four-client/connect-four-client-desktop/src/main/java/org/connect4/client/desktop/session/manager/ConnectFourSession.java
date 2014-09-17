package org.connect4.client.desktop.session.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.connect4.entities.User;

/**
 * The Class ConnectFourSession.
 */
public class ConnectFourSession implements DesktopSession {

    /** The session map. */
    private final Map<String, Object> sessionMap = Collections.synchronizedMap(new HashMap<String, Object>());

    /**
     * Adds the.
     * @param key
     *            the key
     * @param value
     *            the object
     * @return true, if successful
     */
    @Override
    public boolean set(final String key, final Object value) {

        if (this.sessionMap != null && key != null && !USER.equals(key)) {
            this.sessionMap.put(key, value);
            return true;
        }

        return false;
    }

    /**
     * Removes the.
     * @param key
     *            the key
     * @return true, if successful
     */
    @Override
    public boolean remove(final String key) {

        if (this.sessionMap != null && key != null && !USER.equals(key) && this.sessionMap.containsKey(key)) {
            this.sessionMap.remove(key);
            return true;
        }

        return false;
    }

    /**
     * Gets the user.
     * @return the user
     */
    @Override
    public User getUser() {
        if (this.sessionMap != null && this.sessionMap.containsKey(USER)) {
            final User user = (User) this.sessionMap.get(USER);
            if (user != null) {
                return new User(user);
            }
        }

        return null;
    }

    /**
     * Removes the user.
     * @return true, if successful
     */
    @Override
    public boolean removeUser() {
        if (this.sessionMap != null && this.sessionMap.containsKey(USER)) {
            this.sessionMap.remove(USER);
            return true;
        }

        return false;
    }

    /**
     * Clean.
     */
    @Override
    public void clean() {
        if (this.sessionMap != null) {
            this.sessionMap.clear();
        }
    }

    /**
     * Define the session user.
     * @param user
     *            the new user
     */
    @Override
    public void setUser(final User user) {
        if (this.sessionMap != null) {
            this.sessionMap.put(USER, user);
        }

    }

}
