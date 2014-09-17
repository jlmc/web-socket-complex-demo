package org.connect4.client.desktop.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.apache.log4j.Logger;
import org.connect4.client.desktop.MainApp;
import org.connect4.client.desktop.screens.ControlledScreen;
import org.connect4.client.desktop.screens.ScreensController;
import org.connect4.client.endpoint.Notificable;
import org.connect4.client.endpoint.Router;
import org.connect4.entities.User;
import org.connect4.messages.CreateAcountRequestMessage;
import org.connect4.messages.CreateAcountResponseMessage;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;
import org.connect4.utils.functions.Preconditions;

/**
 * The Class RegisterScreenController.
 */
public class RegisterScreenController implements Initializable, ControlledScreen, Notificable {

    /** The Constant LOGGER. */
    @SuppressWarnings("unused")
    private static final Logger LOGGER = Logger.getLogger(RegisterScreenController.class);

    /** The username tx. */
    @FXML
    private TextField usernameTx;

    /** The Create user bt. */
    @FXML
    private Button CreateUserBt;

    /** The error message. */
    @FXML
    private Label errorMessage;

    /** The confirm password tx. */
    @FXML
    private PasswordField confirmPasswordTx;

    /** The displayname tx. */
    @FXML
    private TextField displaynameTx;

    /** The password tx. */
    @FXML
    private PasswordField passwordTx;

    /** The screen page. */
    private ScreensController screenPage;

    /**
     * Creates the user action.
     * @param event
     *            the event
     */
    @FXML
    void createUserAction(final ActionEvent event) {
        try {
            this.CreateUserBt.setDisable(true);
            Preconditions.checkArgument((this.displaynameTx.getText() != null && !this.displaynameTx.getText().trim()
                    .isEmpty()), "Display Name is Mandatory.");

            Preconditions.checkArgument((this.usernameTx.getText() != null && !this.usernameTx.getText().trim()
                    .isEmpty()), "User is Mandatory.");
            Preconditions.checkArgument((this.passwordTx.getText() != null && !this.passwordTx.getText().trim()
                    .isEmpty()), "Password is Mandatory.");
            Preconditions.checkArgument((this.usernameTx.getText() != null && !this.usernameTx.getText().trim()
                    .isEmpty()), "User is Mandatory.");

            Preconditions.checkArgument(this.usernameTx.getText().trim()
                    .equals(this.confirmPasswordTx.getText().trim()), "Password and Confirm password do not matt.");

            final CreateAcountRequestMessage request = new CreateAcountRequestMessage();
            final User user = new User();

            user.setDisplayName(this.displaynameTx.getText().trim());
            user.setUsername(this.usernameTx.getText().trim());
            user.setPassword(this.passwordTx.getText().trim());
            request.setUser(user);

            Router.getInstance().subscribe(this, MessageType.CREATE_ACOUNT_RESPONSE);
            Router.getInstance().sendMessage(request);

            this.errorMessage.setText("");
        } catch (final Exception e) {
            this.errorMessage.setText(e.getMessage());
        }
    }

    /**
     * Back action.
     * @param event
     *            the event
     */
    @FXML
    void backAction(final ActionEvent event) {
        this.screenPage.setScreen(MainApp.LOGIN);
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.screens.ControlledScreen#setScreenParent(org.connect4.client.desktop.screens.
     * ScreensController)
     */
    @Override
    public void setScreenParent(final ScreensController screenPage) {
        this.screenPage = screenPage;
    }

    /*
     * (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        /*
         * this.location = location;
         * this.resources = resources;
         */

    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.screens.ControlledScreen#enable()
     */
    @Override
    public void enable() {
        // Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.endpoint.Notificable#update(org.connect4.messages.Message)
     */
    @Override
    public void update(final Message message) {

        Router.getInstance().unSubscribe(this, MessageType.CREATE_ACOUNT_RESPONSE);

        final CreateAcountResponseMessage response = (CreateAcountResponseMessage) message;
        javafx.application.Platform.runLater(new Runnable() {

            @SuppressWarnings("synthetic-access")
            @Override
            public void run() {

                switch (response.getStatus()) {
                case USER_ALREADY_IN_USE:
                case MISSING_USERNAME:
                    RegisterScreenController.this.errorMessage.setText("User already in use.");
                    break;
                case OK:
                    RegisterScreenController.this.errorMessage.setText("Sucess.");
                    break;
                default:
                    RegisterScreenController.this.errorMessage.setText("Server error...");
                }

                RegisterScreenController.this.CreateUserBt.setDisable(false);

            }
        });

    }
}
