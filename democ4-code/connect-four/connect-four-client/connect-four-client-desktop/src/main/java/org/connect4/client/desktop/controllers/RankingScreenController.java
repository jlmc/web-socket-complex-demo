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
 * The Class RankingScreenController.
 */
public class RankingScreenController implements Initializable, ControlledScreen {

    /** The screen page. */
    private ScreensController screenPage;

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.screens.ControlledScreen#setScreenParent(org.connect4.client.desktop.screens.
     * ScreensController)
     */
    @Override
    public void setScreenParent(final ScreensController screenPage) {
        this.screenPage = screenPage;
    }

    /**
     * Initializes the controller class.
     * @param arg0
     *            the arg0
     * @param arg1
     *            the arg1
     */
    @Override
    public void initialize(final URL arg0, final ResourceBundle arg1) {
        // Auto-generated method stub
    }

    /**
     * Go to general.
     * @param event
     *            the event
     */
    @FXML
    void goToGeneral(final ActionEvent event) {
        this.screenPage.setScreen(MainApp.MAIN);
    }

    @Override
    public void enable() {
        // TODO Auto-generated method stub

    }

}
