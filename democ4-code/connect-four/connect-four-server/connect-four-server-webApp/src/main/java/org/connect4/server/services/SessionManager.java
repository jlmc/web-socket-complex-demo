package org.connect4.server.services;

import java.util.Collection;
import java.util.Set;

import javax.websocket.Session;

import org.connect4.entities.User;
import org.connect4.server.services.exeptions.SessionManagerExeption;

/**
 * The Interface SessionManager.
 */
public interface SessionManager {

    /** The Constant USER_STRING. */
    static final String USER_STRING = "USER";

    /**
     * Adds the session.
     * @param session
     *            the session
     * @return true, if adds the session
     * @throws SessionManagerExeption
     *             the session manager exeption
     */
    boolean addSession(Session session) throws SessionManagerExeption;

    /**
     * Adds the session.
     * @param session
     *            the session
     * @param user
     *            the user
     * @return true, if adds the session, false otherwise
     * @throws SessionManagerExeption
     *             the session manager exeption
     */
    boolean addSession(Session session, User user) throws SessionManagerExeption;

    /**
     * Removes the session.
     * @param session
     *            the session
     * @return true, if removes the session, false otherwise
     * @throws SessionManagerExeption
     *             the session manager exeption
     */
    boolean removeSession(Session session) throws SessionManagerExeption;

    /**
     * Gets the sessions.
     * @return the sessions
     */
    Collection<Session> getSessions();

    /**
     * Gets the users.
     * @return the users
     */
    Collection<User> getUsers();

    /**
     * Gets the user, that are associated with the session passed as parameter.
     * @param session
     *            the session
     * @return the user
     */
    User getUser(Session session);

    /**
     * Sets the notification event manager.
     * @param notificationEventManager
     *            the notification event manager
     */
    void setNotificationEventManager(NotificationEventManager notificationEventManager);

    /**
     * Get all Sessions associeted with the user passed as parameter.
     * @param user
     *            that we want to know the sessions.
     * @return a collection with all the sessions of the user.
     */
    Set<Session> getSessionOf(final User user);

}
