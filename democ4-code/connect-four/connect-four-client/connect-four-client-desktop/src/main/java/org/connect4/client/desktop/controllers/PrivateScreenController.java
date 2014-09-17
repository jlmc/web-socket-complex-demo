package org.connect4.client.desktop.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import org.connect4.client.desktop.MainApp;
import org.connect4.client.desktop.screens.ControlledScreen;
import org.connect4.client.desktop.screens.ScreensController;

/**
 * The Class PrivateScreenController.
 */
public class PrivateScreenController implements Initializable, ControlledScreen {

    /** The screens controller. */
    private ScreensController screensController;

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.screens.ControlledScreen#setScreenParent(org.connect4.client.desktop.screens.
     * ScreensController)
     */
    @Override
    public void setScreenParent(final ScreensController screenPage) {
        this.screensController = screenPage;

    }

    /*
     * (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }

    /**
     * Go to general.
     * @param event
     *            the event
     */
    @FXML
    protected void goToGeneral(final ActionEvent event) {
        this.screensController.setScreen(MainApp.MAIN);
    }

    @Override
    public void enable() {
        // TODO Auto-generated method stub

    }

}
