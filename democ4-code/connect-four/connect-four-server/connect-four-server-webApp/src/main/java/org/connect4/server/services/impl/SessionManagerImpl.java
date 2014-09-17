package org.connect4.server.services.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.websocket.Session;

import org.connect4.entities.User;
import org.connect4.server.services.NotificationDispatcher;
import org.connect4.server.services.NotificationEvent;
import org.connect4.server.services.NotificationEvent.NotificationTypeEnum;
import org.connect4.server.services.NotificationEventManager;
import org.connect4.server.services.SessionManager;
import org.connect4.server.services.exeptions.SessionManagerExeption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SessionManagerImpl.
 */
// @Startup
// @Singleton
@Named
@ApplicationScoped
public class SessionManagerImpl implements SessionManager, NotificationDispatcher {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManagerImpl.class);

    /** The peers map. */
    private final Map<User, Set<Session>> peersMap;

    /** The peers. */
    private final Map<Session, User> peers;

    /** The notification event manager. */
    private NotificationEventManager notificationEventManager;

    /**
     * Instantiates a new session manager impl.
     */
    public SessionManagerImpl() {
        super();
        this.peersMap = Collections.synchronizedMap(new HashMap<User, Set<Session>>());
        this.peers = Collections.synchronizedMap(new HashMap<Session, User>());

    }

    /**
     * Sets the notification event manager.
     * @param notificationEventManager
     *            the notification event manager
     */
    @Override
    public void setNotificationEventManager(final NotificationEventManager notificationEventManager) {
        this.notificationEventManager = notificationEventManager;

    }

    /**
     * Adds the session.
     * Log In to add to the list of sessions to authenticated users {@link User}
     * @param session
     *            the session
     * @return true, if adds the session
     * @throws SessionManagerExeption
     *             the session manager exeption,
     *             is thrown when the session user does not have the properties in their User,
     *             when <code>session.getUserProperties().get(USER_STRING)</code> is NULL
     */
    @Override
    public boolean addSession(final Session session) throws SessionManagerExeption {
        if (session != null) {
            if (session.isOpen()) {
                final User user = (User) session.getUserProperties().get(USER_STRING);
                if (user != null) {

                    final Set<Session> sessionsUser = this.peersMap.get(user);

                    if (this.peersMap.containsKey(user) && sessionsUser != null && !sessionsUser.contains(session)) {
                        // The user is online but in a different session to this session adds to his list
                        LOGGER.info(
                                "The user {} is online but in a different session to this session adds to his list",
                                String.valueOf(user.getUsername()));
                        sessionsUser.add(session);
                        this.peers.put(session, user);
                        return false;

                    } else if (!this.peersMap.containsKey(user)) {
                        // The User don't are onLine, put hin on peerMap this The present Session on that Set
                        LOGGER.info(
                                "The User {} don't are onLine, put hin on peerMap this The present Session on that Set",
                                String.valueOf(user.getUsername()));
                        final HashSet<Session> sessions = new HashSet<>();
                        sessions.add(session);
                        this.peersMap.put(user, sessions);
                        this.peers.put(session, user);
                        // dispache the event sing in to the user
                        dispatch(NotificationTypeEnum.SIGN_IN, getSessions(), session, user);
                        return true;
                    }

                    return false;
                }
                throw new SessionManagerExeption("session user can not ben NULL");
            }
            throw new SessionManagerExeption("session is not open");
        }
        throw new SessionManagerExeption("session can not ben NULL");
    }

    /**
     * Adds the session.
     * Log In to add to the list of sessions to authenticated users {@link User}
     * @param session
     *            the session
     * @param user
     *            the user
     * @return true, if adds the session
     * @throws SessionManagerExeption
     *             the session manager exeption,
     *             is thrown when the session user does not have the properties in their User,
     *             when <code>session.getUserProperties().get(USER_STRING)</code> is NULL
     */
    @Override
    public boolean addSession(final Session session, final User user) throws SessionManagerExeption {
        if (session != null) {
            session.getUserProperties().put(USER_STRING, user);
        }
        return this.addSession(session);
    }

    /**
     * Remove a session from.
     * returns false if the user is no session at the end of the process, true if the user has an open session
     * @param session
     *            the session
     * @return false if the user have no session at the end of the process, true still any session open
     * @throws SessionManagerExeption
     *             the session manager exeption, if the session passed as paramiter is null
     */
    @Override
    public boolean removeSession(final Session session) throws SessionManagerExeption {
        boolean haveAnySession = false;
        if (session != null) {
            final User user = this.peers.get(session);
            if (user != null) {

                haveAnySession = true;

                LOGGER.info("User {}  exit from the session {} ", String.valueOf(user.getUsername()),
                        String.valueOf(session.getId()));

                final Set<Session> sessions = this.peersMap.get(user);
                sessions.remove(session);

                this.peers.remove(sessions);

                session.getUserProperties().remove(USER_STRING);

                if (sessions.isEmpty()) {
                    this.peersMap.remove(user);

                    dispatch(NotificationTypeEnum.SIGN_OUT, getSessions(), session, user);

                    LOGGER.info("User {}  don't have any session, so it does down ", String.valueOf(user.getUsername()));
                    haveAnySession = false;
                }
            }

            return haveAnySession;
        }
        throw new SessionManagerExeption("can't remove a NULL Session");

    }

    /**
     * get the All Session authenticated.
     * @return the sessions
     */
    @Override
    public Collection<Session> getSessions() {
        return Collections.unmodifiableSet(new HashSet<>(this.peers.keySet()));
    }

    /**
     * Removes the session.
     * @return true, if removes the session, false otherwise
     */
    @Override
    public Collection<User> getUsers() {
        return Collections.unmodifiableSet(new HashSet<>(this.peersMap.keySet()));
    }

    /**
     * get the User that are using that Session.
     * @param session
     *            the session
     * @return the user
     */
    @Override
    public User getUser(final Session session) {
        if (this.peers.containsKey(session)) {
            return this.peers.get(session);
        }
        return null;
    }

    /**
     * dispatch to event notification to the event Manager.
     * @param event
     *            the event
     * @param reported
     *            the reported
     * @param about
     *            the about
     * @param user
     *            the user
     */
    @Override
    public void dispatch(final NotificationTypeEnum event, final Collection<Session> reported, final Session about,
            final User user) {
        if (event != null && reported != null && about != null) {
            if (this.notificationEventManager != null) {
                this.notificationEventManager.executeEvent(new NotificationEvent(event, reported, about, user));
                LOGGER.info("dispatch type {}", event);
            } else {
                LOGGER.warn("no notificationEventManager defined the notification {} won't be sent.", event);
            }
        }
    }

    /**
     * Get all Sessions associeted with the user passed as parameter.
     * @param user
     *            that we want to know the sessions
     * @return a collection with all the sessions of the user
     */
    @Override
    public Set<Session> getSessionOf(final User user) {
        if (this.peersMap.containsKey(user)) {
            return Collections.unmodifiableSet(new HashSet<>(this.peersMap.get(user)));
        }
        return Collections.unmodifiableSet(new HashSet<Session>());
    }

}
