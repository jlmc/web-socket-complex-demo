package org.connect4.client.desktop.components;

import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import org.connect4.client.desktop.models.UserWrapper;

/**
 * The Class UserListCell.
 */
public class UserListCell extends ListCell<UserWrapper> {

    /** The tooltip. */
    private final Tooltip tooltip = new Tooltip();

    public UserListCell() {
        super();

    }

    /*
     * (non-Javadoc)
     * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
     */

    @Override
    public void updateItem(final UserWrapper obj, final boolean empty) {
        super.updateItem(obj, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (obj.getWaitingNotification().intValue() <= 0) {
                setText(obj.getUser().getUsername());
                this.tooltip.setText(obj.getUser().getDisplayName());
                setTooltip(this.tooltip);
                // final ImageView butt = new ImageView("images/tags2.png");
                final ImageView butt = new ImageView("images/cellItem.gif");
                butt.setEffect(new DropShadow(15, Color.WHITE));
                setGraphic(butt);
            } else {
                setText(obj.getUser().getUsername());
                this.tooltip.setText(obj.getUser().getDisplayName());
                setTooltip(this.tooltip);
                final ImageView butt = new ImageView("images/tags2.png");
                // final ImageView butt = new ImageView("images/cellItem.gif");
                butt.setEffect(new DropShadow(15, Color.WHITE));
                setGraphic(butt);

            }
        }

    }

}
