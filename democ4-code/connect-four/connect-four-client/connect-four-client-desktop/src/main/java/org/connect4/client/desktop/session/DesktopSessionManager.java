package org.connect4.client.desktop.session;

import org.connect4.client.desktop.session.manager.ConnectFourSession;
import org.connect4.client.desktop.session.manager.DesktopSession;
import org.connect4.entities.User;

/**
 * The Class DesktopSessionManager.
 */
public final class DesktopSessionManager {

    /** The instance. */
    private static DesktopSessionManager instance;

    /** The Constant LOCK. */
    private static final Object LOCK = new Object();

    /** The session. */
    private final DesktopSession session;

    /**
     * Instantiates a new desktop session manager.
     */
    private DesktopSessionManager() {
        super();

        this.session = new ConnectFourSession();

    }

    /**
     * Gets the single instance of DesktopSessionManager.
     * @return single instance of DesktopSessionManager
     */
    public static DesktopSessionManager getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new DesktopSessionManager();
            }
        }
        return instance;
    }

    /**
     * Gets the user.
     * @return the user
     */
    public static User getUser() {
        return DesktopSessionManager.getInstance().session.getUser();
    }

    /**
     * Sets the user.
     * @param user
     *            the new user
     */
    public static void setUser(final User user) {
        DesktopSessionManager.getInstance().session.setUser(user);
    }
}
