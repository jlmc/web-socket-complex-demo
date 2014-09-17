package org.connect4.client.desktop.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;
import org.connect4.client.desktop.models.UserWrapper;
import org.connect4.client.desktop.session.DesktopSessionManager;
import org.connect4.client.endpoint.Router;
import org.connect4.entities.Conversation;
import org.connect4.entities.User;
import org.connect4.messages.Message;
import org.connect4.messages.PrivateChatMessage;
import org.connect4.utils.functions.Preconditions;

/**
 * The Class PrivateConversationController.
 */
public class PrivateConversationController implements Initializable, InnerController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(PrivateConversationController.class);
    // LoggerFactory.getLogger(PrivateConversationController.class);

    /** The conversation history. */
    @FXML
    private TextArea conversationHistory;

    /** The private conversation content. */
    @FXML
    private AnchorPane privateConversationContent;

    /** The edit message txt. */
    @FXML
    private TextArea editMessageTxt;

    /** The titled. */
    @FXML
    private TitledPane titled;

    /** The board pane. */
    @FXML
    private AnchorPane boardPane;

    /** The father. */
    @SuppressWarnings("unused")
    private ChatGeneralScreenXController father;

    /** The data source. */
    private UserWrapper dataSource;

    private PrivateConversationControllerService service;

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

    /**
     * Send event.
     */
    private void sendEvent() {
        try {
            Preconditions.checkNotNull(this.editMessageTxt, "No edit Message Area defined.");
            Preconditions.checkNotNull(this.editMessageTxt.getText(), "Edit Text is NULL.");
            Preconditions.checkArgument(!this.editMessageTxt.getText().isEmpty(), "Edit Text is empty");

            final PrivateChatMessage message = new PrivateChatMessage();

            message.setMessage(this.editMessageTxt.getText());
            final User user = DesktopSessionManager.getUser();
            message.setSender(user);

            message.setConversation(this.dataSource.getConversation());

            message.setConversation(new Conversation(user, this.dataSource.getUser()));

            this.service.send(message);

            this.editMessageTxt.clear();
        } catch (final Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.service = new PrivateConversationControllerService();
    }

    /**
     * Sets the father.
     * @param father
     *            the new father
     */
    @Override
    public void setFather(final ChatGeneralScreenXController father) {
        this.father = father;
    }

    /**
     * Sets the data source.
     * @param userWrapper
     *            the new data source
     */
    @Override
    public void setDataSource(final UserWrapper userWrapper) {
        if (userWrapper != null && !userWrapper.equals(this.dataSource)) {

            if (this.dataSource != null) {
                this.dataSource.setShow(false);
            }

            this.dataSource = userWrapper;
            this.dataSource.setShow(true);
            if (this.dataSource != null) {
                final String title = (this.dataSource.getUser().getDisplayName() == null || this.dataSource.getUser()
                        .getDisplayName().trim().isEmpty()) ? this.dataSource.getUser().getUsername() : this.dataSource
                        .getUser().getDisplayName();

                        Bindings.bindBidirectional(this.conversationHistory.textProperty(),
                                this.dataSource.someStringProperty());

                        this.titled.setText(title);
            }

        }

    }

    class PrivateConversationControllerService {

        public void send(final Message message) {
            if (message != null) {
                Router.getInstance().sendMessage(message);
            }
        }

        public void start() {
            // stubs
        }

        public void stop() {
            // stubs
        }
    }
}
