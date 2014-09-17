package org.connect4.server.services.handlers;

import org.connect4.server.services.ConnectFourMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AbstractConnectFourMessageHandler.
 * AbstractConnectFourMessageHandler, defining a convenience method for sending text messages.
 */
public abstract class AbstractConnectFourMessageHandler implements ConnectFourMessageHandler {

    /** The Constant LOGGER. */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConnectFourMessageHandler.class);

    /**
     * The Constructor.
     */
    public AbstractConnectFourMessageHandler() {
        super();
    }

}
