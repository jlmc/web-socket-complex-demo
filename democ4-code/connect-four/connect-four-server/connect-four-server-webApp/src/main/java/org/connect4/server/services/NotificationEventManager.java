package org.connect4.server.services;

/**
 * The Interface NotificationEventManager.
 */
public interface NotificationEventManager {

    /**
     * Execute event.
     * @param event
     *            the event
     */
    public void executeEvent(NotificationEvent event);

    /**
     * Stop.
     */
    public void stop();

}
