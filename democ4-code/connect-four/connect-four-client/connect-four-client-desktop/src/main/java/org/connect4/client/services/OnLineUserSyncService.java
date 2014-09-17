package org.connect4.client.services;

import java.util.ArrayList;
import java.util.List;

import org.connect4.client.endpoint.Notificable;
import org.connect4.client.endpoint.Router;
import org.connect4.entities.User;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;
import org.connect4.messages.OnLineUsersRequestMessage;
import org.connect4.messages.OnLineUsersResponseMessage;

/**
 * The Class OnLineUserSyncService.
 * This class allows you to obtain the list of users synchronously,
 * ie it is a box that encapsulates a sending a message to
 * the Router subsequently determined expecting a reply.
 */
public class OnLineUserSyncService {

    /** The on line users. */
    @SuppressWarnings("unused")
    private final List<User> onLineUsers = new ArrayList<User>();

    /** The was signalled. */
    private boolean wasSignalled = false;

    /** The router. */
    private final Router router = Router.getInstance();

    /** The notificable. */
    private final Notificable notificable = new Notificable() {

        @SuppressWarnings("synthetic-access")
        @Override
        public void update(final Message message) {
            synchronized (OnLineUserSyncService.this.onLineUsers) {

                if (message != null && MessageType.ON_LINE_USERS_RESPONSE.equals(message.getType())) {
                    final OnLineUsersResponseMessage response = (OnLineUsersResponseMessage) message;

                    OnLineUserSyncService.this.onLineUsers.clear();

                    OnLineUserSyncService.this.onLineUsers.addAll(response.getOnLineUsers());

                    OnLineUserSyncService.this.router.unSubscribe(this, MessageType.ON_LINE_USERS_RESPONSE);

                    OnLineUserSyncService.this.wasSignalled = true;
                    OnLineUserSyncService.this.onLineUsers.notify();

                }
            }

        }
    };

    /**
     * Do wait.
     * @return the list< user>
     */
    private List<User> doWait() {
        synchronized (this.onLineUsers) {
            if (!this.wasSignalled) {
                try {
                    this.onLineUsers.wait();
                } catch (final InterruptedException e) {
                    System.out.println(e.toString());
                }
            }
            // clear signal and continue running.
            this.wasSignalled = false;
        }
        return this.onLineUsers;
    }

    /**
     * Gets the on line users.
     * @return the on line users
     */
    public List<User> getOnLineUsers() {
        this.router.subscribe(this.notificable, MessageType.ON_LINE_USERS_RESPONSE);
        final OnLineUsersRequestMessage request = new OnLineUsersRequestMessage();

        this.router.sendMessage(request);
        return doWait();
    }

}
