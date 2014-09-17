package org.connect4.client.desktop.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import org.connect4.client.desktop.MainApp;
import org.connect4.client.desktop.screens.ControlledScreen;
import org.connect4.client.desktop.screens.ScreensController;

/**
 * The Class GeneralScreenController.
 */
public class GeneralScreenController implements Initializable, ControlledScreen {

    /** The screens controller. */
    private ScreensController screensController;

    /** The user list. */
    @FXML
    private ListView<?> userList;

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.screens.ControlledScreen#setScreenParent(org.connect4.client.desktop.screens.
     * ScreensController)
     */
    @Override
    public void setScreenParent(final ScreensController screenParent) {
        this.screensController = screenParent;
    }

    /**
     * Initializes the controller class.
     * @param url
     *            the url
     * @param resourceBundle
     *            the resource bundle
     */
    @Override
    public void initialize(final URL url, final ResourceBundle resourceBundle) {
        // Auto-generated method stub
    }

    /**
     * Go to ranking.
     * @param event
     *            the event
     */
    @FXML
    protected void goToRanking(final ActionEvent event) {
        // this.screensController.setScreen(MainApp.RANKING);

        // new OnLineUserSyncService().getOnLineUsers();
    }

    /**
     * Logout.
     * @param event
     *            the event
     */
    @FXML
    protected void logout(final ActionEvent event) {
        this.screensController.setScreen(MainApp.LOGIN);
    }

    @Override
    public void enable() {
        // TODO Auto-generated method stub

    }
}
