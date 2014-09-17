package org.connect4.client.endpoint;

import org.connect4.messages.Message;

/**
 * The Interface Notificable.
 */
public interface Notificable {

    /**
     * Update.
     * @param message
     *            the message
     */
    void update(Message message);

}
