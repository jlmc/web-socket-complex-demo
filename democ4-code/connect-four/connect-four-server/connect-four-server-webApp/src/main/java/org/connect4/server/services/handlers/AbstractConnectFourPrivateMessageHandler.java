package org.connect4.server.services.handlers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.connect4.entities.User;
import org.connect4.server.services.handlers.entities.ServerConversationWrapper;

/**
 * The Class AbstractConnectFourPrivateMessageHandler.
 */
public abstract class AbstractConnectFourPrivateMessageHandler extends AbstractConnectFourMessageHandler {

    /** The conversationmap. */
    private static final Map<String, ServerConversationWrapper> CONVERSATIONMAP = Collections
            .synchronizedMap(new HashMap<String, ServerConversationWrapper>(1000));

    /**
     * Instantiates a new abstract connect four private message handler.
     */
    public AbstractConnectFourPrivateMessageHandler() {
        super();
    }

    /**
     * Adds the conversation wrapper.
     * @param conversation
     *            the conversation
     */
    @SuppressWarnings("static-method")
    protected synchronized void addConversationWrapper(final ServerConversationWrapper conversation) {
        final String key = ServerConversationWrapper.generateKey(conversation);
        CONVERSATIONMAP.put(key, conversation);
    }

    /**
     * Gets the conversation wrapper.
     * @param one
     *            the one
     * @param two
     *            the two
     * @return the conversation wrapper, or null if this map contains no mapping for the conversation key
     */
    @SuppressWarnings("static-method")
    protected synchronized ServerConversationWrapper getConversationWrapper(final User one, final User two) {
        final String key = ServerConversationWrapper.generateKey(one, two);
        return CONVERSATIONMAP.get(key);
    }
}
