package org.connect4.client.desktop.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import org.apache.log4j.Logger;
import org.connect4.client.desktop.MainApp;
import org.connect4.client.desktop.screens.ControlledScreen;
import org.connect4.client.desktop.screens.ScreensController;
import org.connect4.client.desktop.session.DesktopSessionManager;
import org.connect4.client.endpoint.Notificable;
import org.connect4.client.endpoint.Notifier;
import org.connect4.client.endpoint.Router;
import org.connect4.messages.LoginRequestMessage;
import org.connect4.messages.LoginResponseMessage;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;
import org.connect4.utils.functions.Preconditions;

/**
 * The Class LoginScreenController.
 */
public class LoginScreenController implements Initializable, ControlledScreen, Notificable {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(LoginScreenController.class);
    // LoggerFactory.getLogger(LoginScreenController.class);

    /** The screens controller. */
    private ScreensController screensController;

    /** The resources. */
    @FXML
    private ResourceBundle resources;

    /** The location. */
    @FXML
    private URL location;

    /** The error message. */
    @FXML
    private Label errorMessage;

    /** The password txt. */
    @FXML
    private PasswordField passwordTxt;

    /** The sign in bt. */
    @FXML
    private Button signInBt;

    /** The username txt. */
    @FXML
    private TextField usernameTxt;

    /** The login panel. */
    @FXML
    private Pane loginPanel;

    /**
     * Sets the screen parent.
     * This method will allow the injection of the Parent ScreenPane
     * @param screenPage
     *            the new screen parent
     */
    @Override
    public void setScreenParent(final ScreensController screenPage) {
        this.screensController = screenPage;

    }

    /** The notifier. */
    private Notifier notifier;

    /*
     * (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        assert this.errorMessage != null : "fx:id=\"errorMessage\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert this.passwordTxt != null : "fx:id=\"passwordTxt\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert this.signInBt != null : "fx:id=\"signInBt\" was not injected: check your FXML file 'LoginScreen.fxml'.";
        assert this.usernameTxt != null : "fx:id=\"usernameTxt\" was not injected: check your FXML file 'LoginScreen.fxml'.";

        this.notifier = Router.getInstance();
        this.notifier.subscribe(this, MessageType.LOGIN_RESPONSE);

    }

    /**
     * Authentication action.
     * @param event
     *            the event
     */
    @FXML
    void authenticationAction(final ActionEvent event) {
        authentication();
    }

    /**
     * On key pressed.
     * @param event
     *            the event
     */
    @FXML
    void onKeyPressed(final KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            authentication();
        }
    }

    /**
     * On missing password action.
     * @param event
     *            the event
     */
    @SuppressWarnings("static-method")
    @FXML
    void onMissingPasswordAction(final ActionEvent event) {
        // TODO missing implementation

        LOGGER.info("RECOVERY PASSWORD IS WAITING FOR MISSING IMPLEMENTATION");
    }

    /**
     * Creates the user click.
     * @param event
     *            the event
     */
    @FXML
    void createUserClick(final ActionEvent event) {
        this.screensController.setScreen(MainApp.REGISTER);
    }

    /**
     * Authentication.
     */
    private void authentication() {
        try {

            this.loginPanel.setDisable(true);

            Preconditions.checkNotNull(this.usernameTxt.getText(), "username can't be null");
            Preconditions.checkNotNull(this.passwordTxt.getText(), "password can't be null");
            Preconditions.checkArgument(!this.usernameTxt.getText().trim().isEmpty(), "username can't be empty");
            Preconditions.checkArgument(!this.passwordTxt.getText().trim().isEmpty(), "password can't be empty");

            final LoginRequestMessage request = new LoginRequestMessage();

            request.setUsername(this.usernameTxt.getText());
            request.setPassword(this.passwordTxt.getText());

            this.notifier.subscribe(this, MessageType.LOGIN_RESPONSE);
            this.notifier.sendMessage(request);

        } catch (final Exception e) {
            this.errorMessage.setText(e.getMessage());
        }

        this.loginPanel.setDisable(false);

    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.endpoint.Notificable#update(org.connect4.messages.Message)
     */
    @Override
    public void update(final Message message) {

        final LoginResponseMessage response = (LoginResponseMessage) message;

        // final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // scheduler.scheduleAtFixedRate(new Runnable() {
        // @Override
        // public void run() {
        Platform.runLater(new Runnable() {
            @SuppressWarnings("synthetic-access")
            @Override
            public void run() {

                switch (response.getStatus()) {
                case OK:

                    LoginScreenController.this.errorMessage.setText("");

                    DesktopSessionManager.setUser(response.getUser());

                    response.getUser();

                    LoginScreenController.this.screensController.setScreen(MainApp.MAIN);
                    break;
                case WRONG_PASSWORD:
                    LoginScreenController.this.errorMessage.setText("Wrong password for the indicated Username");
                    break;
                case NONEXISTENT_USERNAME:
                    LoginScreenController.this.errorMessage.setText("Username don't exist on the system");
                    break;
                case INTERNAL_ERROR:
                    LoginScreenController.this.errorMessage.setText("Internal error on the server");
                    break;
                default:
                    break;
                }

                LoginScreenController.this.loginPanel.setDisable(false);

            }
        });
        this.notifier.unSubscribe(this, MessageType.LOGIN_RESPONSE);
        // }
        // }, 1, 1, TimeUnit.SECONDS);

    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.screens.ControlledScreen#enable()
     */
    @Override
    public void enable() {
        // do nothing
    }

}
