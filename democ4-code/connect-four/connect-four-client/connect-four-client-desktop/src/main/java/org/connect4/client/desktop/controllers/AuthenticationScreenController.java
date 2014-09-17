package org.connect4.client.desktop.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.connect4.client.desktop.MainApp;
import org.connect4.client.desktop.screens.ControlledScreen;
import org.connect4.client.desktop.screens.ScreensController;
import org.connect4.client.endpoint.Notificable;
import org.connect4.client.endpoint.Notifier;
import org.connect4.client.endpoint.Router;
import org.connect4.messages.LoginRequestMessage;
import org.connect4.messages.LoginResponseMessage;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;
import org.connect4.utils.functions.Preconditions;

/**
 * The Class AuthenticationScreenController.
 */
public class AuthenticationScreenController implements Initializable, ControlledScreen, Notificable {

    /** The resources. */
    @FXML
    private ResourceBundle resources;

    /** The location. */
    @FXML
    private URL location;

    /** The password. */
    @FXML
    private TextField password;

    /** The result. */
    @FXML
    private TextArea result;

    /** The username. */
    @FXML
    private TextField username;

    /** The screen page. */
    private ScreensController screenPage;

    /** The Notifier. */
    private final Notifier notifier = Router.getInstance();

    /**
     * On login action.
     * @param event
     *            the event
     */
    @FXML
    void onLoginAction(final ActionEvent event) {
        try {
            Preconditions.checkNotNull(this.username.getText(), "username can't be null or empty");
            Preconditions.checkNotNull(this.password.getText(), "password can't be null or empty");
            Preconditions.checkArgument(!this.username.getText().trim().isEmpty(), "usernmae can't be empty");
            Preconditions.checkArgument(!this.password.getText().trim().isEmpty(), "password can't be empty");
            // this.screenPage.setScreen(MainApp.MAIN);
        } catch (final Exception e) {
            this.result.setText(e.getMessage());
        }

        final LoginRequestMessage request = new LoginRequestMessage();
        request.setUsername(this.username.getText());
        request.setPassword(this.password.getText());

        this.notifier.subscribe(this, MessageType.LOGIN_RESPONSE);
        this.notifier.sendMessage(request);

    }

    /**
     * Creates the user click.
     * @param event
     *            the event
     */
    @FXML
    void createUserClick(final ActionEvent event) {
        // TODO::
    }

    /**
     * On register action.
     * @param event
     *            the event
     */
    @FXML
    void onRegisterAction(final ActionEvent event) {
        this.screenPage.setScreen(MainApp.REGISTER);
    }

    /**
     * Initialize.
     */
    @FXML
    void initialize() {
        assert this.password != null : "fx:id=\"password\" was not injected: check your FXML file 'AuthenticationScreen.fxml'.";
        assert this.result != null : "fx:id=\"result\" was not injected: check your FXML file 'AuthenticationScreen.fxml'.";
        assert this.username != null : "fx:id=\"username\" was not injected: check your FXML file 'AuthenticationScreen.fxml'.";

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
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        this.result.setText("");

    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.endpoint.Notificable#update(org.connect4.messages.Message)
     */
    @Override
    public void update(final Message message) {
        try {
            if (MessageType.LOGIN_RESPONSE.equals(message.getType())) {
                final LoginResponseMessage response = (LoginResponseMessage) message;
                // this.result.appendText(response.getStatus().toString() + "\n");
                // startScheduledExecutorService();
                scheduleUpdateService(response);

            }

        } catch (final Exception e) {
            System.out.println("" + e.toString());
        }

    }

    /**
     * Schedule update service.
     * @param response
     *            the response
     */
    private void scheduleUpdateService(final LoginResponseMessage response) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @SuppressWarnings("synthetic-access")
                    @Override
                    public void run() {

                        AuthenticationScreenController.this.result.setText(response.getStatus().toString());
                        switch (response.getStatus()) {
                        case OK:

                            AuthenticationScreenController.this.screenPage.setScreen(MainApp.MAIN);

                            scheduler.shutdown();

                            AuthenticationScreenController.this.notifier.unSubscribe(
                                    AuthenticationScreenController.this, MessageType.LOGIN_RESPONSE);
                            break;
                        case WRONG_PASSWORD:

                            AuthenticationScreenController.this.result
                            .setText("Wrong password for the indicated Username");

                            break;

                            /** The nonexistent username. */
                        case NONEXISTENT_USERNAME:

                            AuthenticationScreenController.this.result.setText("Username don't exist on the system");
                            break;
                        case INTERNAL_ERROR:
                            AuthenticationScreenController.this.result.setText("Internal error on the server");
                            break;
                        default:
                            break;
                        }

                    }
                });

            }

        }, 1, 1, TimeUnit.SECONDS);
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
     * private void startScheduledExecutorService() {
     * final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
     * scheduler.scheduleAtFixedRate(new Runnable() {
     * int counter = 0;
     * @Override
     * public void run() {
     * this.counter++;
     * if (this.counter <= 10) {
     * Platform.runLater(new Runnable() {
     * @Override
     * public void run() {
     * AuthenticationScreenController.this.result.setText("isFxApplicationThread: "
     * + Platform.isFxApplicationThread() + "\n" + "Counting: "
     * + String.valueOf(counter));
     * }
     * });
     * } else {
     * scheduler.shutdown();
     * Platform.runLater(new Runnable() {
     * @Override
     * public void run() {
     * AuthenticationScreenController.this.result.setText("isFxApplicationThread: "
     * + Platform.isFxApplicationThread() + "\n" + "-Finished-");
     * }
     * });
     * }
     * }
     * }, 1, 1, TimeUnit.SECONDS);
     * }
     */
}
