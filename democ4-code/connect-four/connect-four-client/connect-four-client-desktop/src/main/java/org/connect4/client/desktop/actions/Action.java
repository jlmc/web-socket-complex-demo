/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.connect4.client.desktop.actions;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * The Class Action.
 * @author Carlos
 */
@SuppressWarnings("rawtypes")
public abstract class Action implements EventHandler {

    /*
     * (non-Javadoc)
     * @see javafx.event.EventHandler#handle(javafx.event.Event)
     */
    @Override
    public void handle(final Event t) {
        // Nothing
    }

}
