package org.connect4.client.desktop.interfaces;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

/**
 * The Class NotificationCenter.
 * @author Carlos
 */
public class NotificationCenter {

    /** The pop over. */
    private PopOver popOver = new PopOver();

    /** The master arrow size. */
    private final DoubleProperty masterArrowSize;

    /** The master arrow indent. */
    private final DoubleProperty masterArrowIndent;

    /** The master corner radius. */
    private final DoubleProperty masterCornerRadius;

    /** The master arrow location. */
    private final ObjectProperty<ArrowLocation> masterArrowLocation;

    /**
     * The Constructor.
     * @param bt
     *            the bt
     */
    public NotificationCenter(final Button bt) {
        this.masterArrowSize = new SimpleDoubleProperty(this.popOver.getArrowSize());
        this.masterArrowIndent = new SimpleDoubleProperty(this.popOver.getArrowIndent());
        this.masterCornerRadius = new SimpleDoubleProperty(0);
        this.masterArrowLocation = new SimpleObjectProperty<>(ArrowLocation.TOP_CENTER);

        this.popOver = createPopOver();
        // popOver.setPrefSize(300, 300);
        this.popOver.setDetachable(false);
        // popOver.setContentNode(new LoginPage(""));
        this.popOver.show(bt, 500, 100);

    }

    /**
     * Creates the pop over.
     * @return the pop over
     */
    @SuppressWarnings("hiding")
    private PopOver createPopOver() {
        final PopOver popOver = new PopOver();
        popOver.arrowSizeProperty().bind(this.masterArrowSize);
        popOver.arrowIndentProperty().bind(this.masterArrowIndent);
        popOver.arrowLocationProperty().bind(this.masterArrowLocation);
        popOver.cornerRadiusProperty().bind(this.masterCornerRadius);
        return popOver;
    }

    /**
     * Gets the pop over.
     * @return the pop over
     */
    public PopOver getPopOver() {
        return this.popOver;
    }

    /**
     * Sets the pop over.
     * @param popOver
     *            the pop over
     */
    public void setPopOver(final PopOver popOver) {
        this.popOver = popOver;
    }

}
