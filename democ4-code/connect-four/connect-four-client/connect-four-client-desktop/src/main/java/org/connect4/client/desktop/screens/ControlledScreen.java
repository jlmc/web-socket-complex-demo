package org.connect4.client.desktop.screens;

/**
 * The Interface ControlledScreen.
 */
public interface ControlledScreen {
    // This method will allow the injection of the Parent ScreenPane
    /**
     * Sets the screen parent.
     * This method will allow the injection of the Parent ScreenPane
     * @param screenPage
     *            the new screen parent
     */
    public void setScreenParent(ScreensController screenPage);

    /**
     * Enable.
     */
    public void enable();
}
