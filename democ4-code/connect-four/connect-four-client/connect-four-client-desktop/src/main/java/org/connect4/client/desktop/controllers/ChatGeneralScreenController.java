package org.connect4.client.desktop.controllers;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.connect4.client.desktop.components.UserListCell;
import org.connect4.client.desktop.models.UserWrapper;
import org.connect4.client.desktop.screens.ControlledScreen;
import org.connect4.client.desktop.screens.ScreensController;
import org.connect4.client.desktop.session.DesktopSessionManager;
import org.connect4.client.endpoint.Notificable;
import org.connect4.client.endpoint.Notifier;
import org.connect4.client.endpoint.Router;
import org.connect4.entities.User;
import org.connect4.messages.ChatMessage;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;
import org.connect4.messages.NotificationSignInMessage;
import org.connect4.messages.NotificationSingOutMessage;
import org.connect4.messages.OnLineUsersRequestMessage;
import org.connect4.messages.OnLineUsersResponseMessage;
import org.connect4.utils.functions.Preconditions;

/**
 * The Class ChatGeneralScreenController.
 */
public class ChatGeneralScreenController implements Initializable, ControlledScreen, Notificable {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ChatGeneralScreenController.class);
    // LoggerFactory.getLogger(LoginScreenController.class);

    /** The screens controller. */
    @SuppressWarnings("unused")
    private ScreensController screensController;

    /** The user list. */
    @FXML
    private ListView<UserWrapper> userList;

    /** The conversation history. */
    @FXML
    private TextArea conversationHistory;

    /** The conversation content. */
    @FXML
    private AnchorPane conversationContent;

    /** The edit message txt. */
    @FXML
    private TextArea editMessageTxt;

    /** The notifier. */
    private Notifier notifier;

    /** The users. */
    private final ObservableList<UserWrapper> users = FXCollections.observableArrayList(new HashSet<UserWrapper>());

    /*
     * (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        this.userList.setCellFactory(new Callback<ListView<UserWrapper>, ListCell<UserWrapper>>() {

            @Override
            public ListCell<UserWrapper> call(final ListView<UserWrapper> p) {
                final ListCell<UserWrapper> cell = new UserListCell();
                return cell;
            }
        });
        /*
         * listView.setCellFactory(new Callback<ListView<MyObject>, ListCell<MyObject>>(){
         * @Override
         * public ListCell<MyObject> call(ListView<MyObject> p) {
         * ListCell<MyObject> cell = new ListCell<MyObject>(){
         * @Override
         * protected void updateItem(MyObject t, boolean bln) {
         * super.updateItem(t, bln);
         * if (t != null) {
         * setText(t.getDay() + ":" + t.getNumber());
         * }
         * }
         * };
         * return cell;
         * }
         * });
         */

        this.notifier = Router.getInstance();
        this.notifier.subscribe(this, MessageType.PUBLIC, MessageType.NOTIFICATION_SIGN_IN,
                MessageType.NOTIFICATION_SIGN_OUT);

        this.userList.setItems(this.users);

    }

    /**
     * On key press.
     * @param event
     *            the event
     */
    @FXML
    void onKeyPress(final KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER) && event.isControlDown()) {
            // CTRL + ENTER
            this.editMessageTxt.appendText("\n");
        } else if (event.getCode().equals(KeyCode.ENTER)) {
            sendEvent();
        }

    }

    /**
     * On send action.
     * @param event
     *            the event
     */
    @FXML
    void onSendAction(final ActionEvent event) {
        sendEvent();
    }

    @FXML
    void onSelectUser(final MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {

                final UserWrapper user = this.userList.getSelectionModel().getSelectedItem();

                LOGGER.info(String.format("SelectionOf user %s ", user == null ? "NULL" : user.getUser().getUsername()));

            }
        }
    }

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

    /**
     * Send event.
     */
    private void sendEvent() {
        try {
            Preconditions.checkNotNull(this.editMessageTxt, "No edit Message Area defined.");
            Preconditions.checkNotNull(this.editMessageTxt.getText(), "Edit Text is NULL.");
            Preconditions.checkArgument(!this.editMessageTxt.getText().isEmpty(), "Edit Text is empty");

            final ChatMessage message = new ChatMessage();

            message.setMessage(this.editMessageTxt.getText());

            final User user = DesktopSessionManager.getUser();
            message.setSender(user);

            this.notifier.sendMessage(message);

            this.editMessageTxt.setText("");
        } catch (final Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

    /**
     * update the conversation history.
     * @param message
     *            the message
     */
    @Override
    public void update(final Message message) {

        final MessageType type = message.getType();

        switch (type) {
        case PUBLIC:
            generalChatMessageHandler(message);
            break;
        case ON_LINE_USERS_RESPONSE:

            usersListHandler(message);

            break;
        case NOTIFICATION_SIGN_IN:
            signInHandler(message);
            break;
        case NOTIFICATION_SIGN_OUT:
            signOutHandler(message);
            break;
        default:
            break;
        }

    }

    /**
     * General chat message handler.
     * @param message
     *            the message
     */
    private void generalChatMessageHandler(final Message message) {
        final ChatMessage response = (ChatMessage) message;

        if (response != null) {
            Platform.runLater(new Runnable() {
                @SuppressWarnings("synthetic-access")
                @Override
                public void run() {

                    final User sender = response.getSender();
                    final String msg = response.getMessage();

                    final String str = String.format("%s wrote: \n%s  \n\n", sender.getUsername(), msg);

                    ChatGeneralScreenController.this.conversationHistory.appendText(str);
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.screens.ControlledScreen#enable()
     */
    @Override
    public void enable() {
        // this.notifier.unSubscribe(this, MessageType.NOTIFICATION_SIGN_IN, MessageType.NOTIFICATION_SIGN_OUT);
        this.notifier.subscribe(this, MessageType.ON_LINE_USERS_RESPONSE);

        final OnLineUsersRequestMessage request = new OnLineUsersRequestMessage();
        this.notifier.sendMessage(request);

    }

    /**
     * Users list handler.
     * @param message
     *            the message
     */
    private void usersListHandler(final Message message) {
        final OnLineUsersResponseMessage response = (OnLineUsersResponseMessage) message;
        if (response != null) {
            Platform.runLater(new Runnable() {
                @SuppressWarnings("synthetic-access")
                @Override
                public void run() {
                    ChatGeneralScreenController.this.users.clear();

                    for (final User u : response.getOnLineUsers()) {
                        ChatGeneralScreenController.this.users.add(new UserWrapper(u));
                    }

                    // ChatGeneralScreenController.this.users.addAll(response.getOnLineUsers());

                    ChatGeneralScreenController.this.notifier.subscribe(ChatGeneralScreenController.this,
                            MessageType.NOTIFICATION_SIGN_IN, MessageType.NOTIFICATION_SIGN_OUT);

                }
            });
        }
    }

    /**
     * Sign out handler.
     * @param message
     *            the message
     */
    private void signOutHandler(final Message message) {
        if (message != null) {
            final NotificationSingOutMessage response = (NotificationSingOutMessage) message;

            Platform.runLater(new Runnable() {
                @SuppressWarnings("synthetic-access")
                @Override
                public void run() {
                    final User user = response.getUser();
                    if (user != null && ChatGeneralScreenController.this.users.contains(user)) {
                        ChatGeneralScreenController.this.users.remove(user);
                    }
                }
            });

        }

    }

    /**
     * Sign in handler.
     * @param message
     *            the message
     */
    private void signInHandler(final Message message) {
        if (message != null) {
            final NotificationSignInMessage response = (NotificationSignInMessage) message;

            Platform.runLater(new Runnable() {
                @SuppressWarnings("synthetic-access")
                @Override
                public void run() {
                    final User user = response.getUser();

                    final UserWrapper uw = new UserWrapper(user);

                    if (user != null && !ChatGeneralScreenController.this.users.contains(uw)) {

                        ChatGeneralScreenController.this.users.add(uw);
                    }
                }
            });

        }

    }
}
