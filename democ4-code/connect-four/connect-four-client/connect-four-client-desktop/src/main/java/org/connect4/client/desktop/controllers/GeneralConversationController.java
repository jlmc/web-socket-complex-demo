package org.connect4.client.desktop.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.apache.log4j.Logger;
import org.connect4.client.desktop.models.UserWrapper;
import org.connect4.client.desktop.session.DesktopSessionManager;
import org.connect4.client.endpoint.Notificable;
import org.connect4.client.endpoint.Notifier;
import org.connect4.client.endpoint.Router;
import org.connect4.entities.User;
import org.connect4.messages.ChatMessage;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;
import org.connect4.utils.functions.Preconditions;

/**
 * The Class GeneralConversationController.
 */
public class GeneralConversationController implements Initializable, InnerController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GeneralConversationController.class);

    // LoggerFactory.getLogger(GeneralConversationController.class);

    /** The conversation history. */
    @FXML
    private TextArea conversationHistory;

    /** The edit message txt. */
    @FXML
    private TextArea editMessageTxt;

    /** The general conversation controller service. */
    private GeneralConversationControllerService generalConversationControllerService;

    /** The chat general screen x controller. */
    @SuppressWarnings("unused")
    private ChatGeneralScreenXController chatGeneralScreenXController;

    /**
     * Sets the father.
     * @param chatGeneralScreenXController
     *            the new father
     */
    @Override
    public void setFather(final ChatGeneralScreenXController chatGeneralScreenXController) {
        this.chatGeneralScreenXController = chatGeneralScreenXController;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.connect4.client.desktop.controllers.InnerController#setDataSource(org.connect4.client.desktop.models.UserWrapper
     * )
     */
    @Override
    public void setDataSource(final UserWrapper userWrapper) {
        // Stub
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

            this.generalConversationControllerService.send(message);

            this.editMessageTxt.setText("");
        } catch (final Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
     */
    /**
     * Initialize.
     * @param location
     *            the location
     * @param resources
     *            the resources
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        this.generalConversationControllerService = new GeneralConversationControllerService(this.conversationHistory);
        this.generalConversationControllerService.start();
    }

    /**
     * The Class GeneralConversationControllerService.
     */
    class GeneralConversationControllerService implements Notificable {

        /** The conversation history. */
        @SuppressWarnings("hiding")
        private final TextArea conversationHistory;

        /** The instance. */
        private final GeneralConversationControllerService instance;

        /**
         * Instantiates a new general conversation controller service.
         * @param conversationHistory
         *            the conversation history
         */
        public GeneralConversationControllerService(final TextArea conversationHistory) {
            this.conversationHistory = conversationHistory;
            this.instance = this;
        }

        /**
         * Start.
         */
        public void start() {
            final Notifier n = Router.getInstance();
            n.subscribe(getInstance(), MessageType.PUBLIC);
        }

        /**
         * Stop.
         */
        public void stop() {
            final Notifier n = Router.getInstance();
            n.unSubscribe(getInstance(), MessageType.PUBLIC);
        }

        /**
         * Send.
         * @param message
         *            the message
         */
        public void send(final ChatMessage message) {
            if (message != null) {
                final Notifier n = Router.getInstance();
                n.sendMessage(message);
            }
        }

        /*
         * (non-Javadoc)
         * @see org.connect4.client.endpoint.Notificable#update(org.connect4.messages.Message)
         */
        /**
         * Update.
         * @param message
         *            the message
         */
        @Override
        public void update(final Message message) {
            final MessageType type = message.getType();

            switch (type) {
            case PUBLIC:
                final ChatMessage response = (ChatMessage) message;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        final User sender = response.getSender();
                        final String msg = response.getMessage();
                        final String str = String.format("%s wrote: \n%s  \n\n", sender.getUsername(), msg);
                        getConversationHistory().appendText(str);
                    }
                });
                break;
            default:
                break;
            }
        }

        /**
         * Gets the conversation history.
         * @return the conversation history
         */
        protected TextArea getConversationHistory() {
            return this.conversationHistory;
        }

        /**
         * Gets the single instance of GeneralConversationControllerService.
         * @return single instance of GeneralConversationControllerService
         */
        protected GeneralConversationControllerService getInstance() {
            return this.instance;
        }
    }

}
