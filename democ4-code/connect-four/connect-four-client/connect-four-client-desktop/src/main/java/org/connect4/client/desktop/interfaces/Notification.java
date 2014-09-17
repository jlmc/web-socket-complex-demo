package org.connect4.client.desktop.interfaces;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;

/**
 * The Class Notification.
 * @author Carlos
 */
public class Notification {

    /**
     * The Constructor.
     */
    public Notification() {
        notification(Pos.TOP_LEFT);
    }

    /**
     * Notification.
     * @param pos
     *            the pos
     */
    @SuppressWarnings({"static-method", })
    private void notification(final Pos pos) {
        final String text = "Hello World ";

        final Notifications notificationBuilder = Notifications.create().title("Notificaçção").text(text)
                .hideAfter(Duration.seconds(2)).position(pos).onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent arg0) {
                        System.out.println("Notification clicked on!");
                    }
                });
        notificationBuilder.show();
    }
}
