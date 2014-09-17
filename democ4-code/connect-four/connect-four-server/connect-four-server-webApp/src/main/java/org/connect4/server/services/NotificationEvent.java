package org.connect4.server.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

import org.connect4.entities.User;

/**
 * The Class NotificationEvent.
 */
public class NotificationEvent {

    /**
     * The Enum NotificationTypeEnum.
     */
    public enum NotificationTypeEnum {

        /** The sign in. */
        SIGN_IN,
        /** The sign out. */
        SIGN_OUT;

    }

    /** The type. */
    private NotificationTypeEnum type;

    /** The reported. */
    private Set<Session> reported;

    /** The about. */
    private Session about;

    /** The user. */
    private User user;

    /**
     * Instantiates a new notification event.
     * @param type
     *            the type
     * @param reported
     *            the reported
     * @param about
     *            the about
     * @param user
     *            the user
     */
    public NotificationEvent(final NotificationTypeEnum type, final Collection<Session> reported, final Session about,
            final User user) {
        this.type = type;
        this.reported = (reported != null) ? new HashSet<>(reported) : new HashSet<Session>();
        this.about = about;
        this.user = new User(user);
    }

    /**
     * Gets the type.
     * @return the type
     */
    public NotificationTypeEnum getType() {
        return this.type;
    }

    /**
     * Sets the type.
     * @param type
     *            the new type
     */
    public void setType(final NotificationTypeEnum type) {
        this.type = type;
    }

    /**
     * Gets the reported.
     * @return the reported
     */
    public Set<Session> getReported() {
        return this.reported;
    }

    /**
     * Sets the reported.
     * @param reported
     *            the new reported
     */
    public void setReported(final Set<Session> reported) {
        this.reported = reported;
    }

    /**
     * Gets the about.
     * @return the about
     */
    public Session getAbout() {
        return this.about;
    }

    /**
     * Sets the about.
     * @param about
     *            the new about
     */
    public void setAbout(final Session about) {
        this.about = about;
    }

    /**
     * Gets the user.
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Sets the user.
     * @param user
     *            the new user
     */
    public void setUser(final User user) {
        this.user = user;
    }

}
