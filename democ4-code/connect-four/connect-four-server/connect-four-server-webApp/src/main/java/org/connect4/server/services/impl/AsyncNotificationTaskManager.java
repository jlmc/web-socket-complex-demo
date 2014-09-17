package org.connect4.server.services.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.websocket.Session;

import org.connect4.entities.User;
import org.connect4.messages.Message;
import org.connect4.messages.NotificationSignInMessage;
import org.connect4.messages.NotificationSingOutMessage;
import org.connect4.server.helpers.SendMessagerHelper;
import org.connect4.server.services.NotificationEvent;
import org.connect4.server.services.NotificationEventManager;
import org.connect4.utils.functions.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AsyncNotificationTaskManager.
 */
// @Startup
// @Singleton

@Named
@ApplicationScoped
public class AsyncNotificationTaskManager implements NotificationEventManager {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncNotificationTaskManager.class);

    /** The scheduled executor service. */
    private final ScheduledExecutorService scheduledExecutorService; // = Executors.newScheduledThreadPool(10);

    /**
     * The Constructor.
     */
    public AsyncNotificationTaskManager() {
        super();
        this.scheduledExecutorService = Executors.newScheduledThreadPool(10);
    }

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        LOGGER.info("ready to work: {}", this);
    }

    /**
     * Execute event.
     * @param event
     *            the event
     */
    @Override
    public void executeEvent(final NotificationEvent event) {
        try {
            Preconditions.checkNotNull(event, "event is null");
            Preconditions.checkNotNull(event.getAbout(), "session about is null");
            Preconditions.checkNotNull(event.getReported(), "sessions to resport are null");
            Preconditions.checkNotNull(event.getType(), "No type defined on event");
            Preconditions.checkNotNull(event.getUser(), "No User defined on  event");
        } catch (final Exception e) {
            LOGGER.info("Event not processed because: {} ", e.getMessage());
            return;
        }

        switch (event.getType()) {
        case SIGN_IN:
            LOGGER.info("SIGN IN event notification. ");
            final NotificationSignInMessage signInMessage = new NotificationSignInMessage();
            signInMessage.setToken(event.getAbout().getId());
            signInMessage.setUser(new User(event.getUser()));

            final HashSet<Session> sendtoPeers = new HashSet<>(event.getReported());
            sendtoPeers.remove(event.getAbout());
            if (!sendtoPeers.isEmpty()) {
                this.executeEvent(sendtoPeers, signInMessage);
            }
            break;
        case SIGN_OUT:
            LOGGER.info("SIGN OUT event notification. ");
            final NotificationSingOutMessage signOutMessage = new NotificationSingOutMessage();
            signOutMessage.setToken(event.getAbout().getId());
            signOutMessage.setUser(new User(event.getUser()));

            final HashSet<Session> sendtoPeersout = new HashSet<>(event.getReported());
            sendtoPeersout.remove(event.getAbout());
            if (!sendtoPeersout.isEmpty()) {
                this.executeEvent(sendtoPeersout, signOutMessage);
            }
            break;
        default:
            break;
        }

    }

    /**
     * Execute event.
     * @param sessions
     *            the sessions
     * @param message
     *            the message
     */
    private void executeEvent(final Collection<Session> sessions, final Message message) {
        this.scheduledExecutorService.schedule(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                SendMessagerHelper.sendMessageTo(sessions, message);

                return Boolean.TRUE;
            }

        }, 200, TimeUnit.MILLISECONDS);
    }

    /**
     * Stop the scheduledExecutorService.
     */
    @Override
    public void stop() {
        this.scheduledExecutorService.shutdown();
    }
}
