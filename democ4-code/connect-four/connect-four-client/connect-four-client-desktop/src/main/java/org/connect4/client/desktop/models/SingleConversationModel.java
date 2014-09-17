package org.connect4.client.desktop.models;

import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;

import org.connect4.entities.Conversation;

public class SingleConversationModel implements EventDispatcher {

    private Conversation conversation;

    public SingleConversationModel() {
        super();
        // this.dispatchEvent(new , tail)
    }

    @Override
    public Event dispatchEvent(final Event event, final EventDispatchChain tail) {
        // TODO Auto-generated method stub
        return null;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public void setConversation(final Conversation conversation) {
        this.conversation = conversation;
    }

}
