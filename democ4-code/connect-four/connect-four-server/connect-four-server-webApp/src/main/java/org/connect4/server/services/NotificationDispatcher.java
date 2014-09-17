package org.connect4.server.services;

import java.util.Collection;

import javax.websocket.Session;

import org.connect4.entities.User;
import org.connect4.server.services.NotificationEvent.NotificationTypeEnum;

/**
 * The Interface NotificationDispatcher.
 */
public interface NotificationDispatcher {

    /**
     * Dispatch.
     * @param event
     *            the event
     * @param reported
     *            the reported
     * @param about
     *            the about
     * @param user
     *            the user
     */
    public void dispatch(final NotificationTypeEnum event, final Collection<Session> reported,
            final Session about, final User user);

}
