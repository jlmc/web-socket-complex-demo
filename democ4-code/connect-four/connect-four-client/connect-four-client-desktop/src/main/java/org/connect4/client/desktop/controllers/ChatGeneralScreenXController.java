package org.connect4.client.desktop.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.connect4.client.desktop.components.UserListCell;
import org.connect4.client.desktop.controllers.services.PrivateMessagesService;
import org.connect4.client.desktop.models.UserWrapper;
import org.connect4.client.desktop.screens.ControlledScreen;
import org.connect4.client.desktop.screens.ScreensController;
import org.connect4.client.desktop.session.DesktopSessionManager;
import org.connect4.client.endpoint.Notificable;
import org.connect4.client.endpoint.NotificableService;
import org.connect4.client.endpoint.Notifier;
import org.connect4.client.endpoint.Router;
import org.connect4.entities.Conversation;
import org.connect4.entities.User;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;
import org.connect4.messages.NotificationSignInMessage;
import org.connect4.messages.NotificationSingOutMessage;
import org.connect4.messages.OnLineUsersRequestMessage;
import org.connect4.messages.OnLineUsersResponseMessage;

/**
 * The Class ChatGeneralScreenXController.
 */
public class ChatGeneralScreenXController implements Initializable, ControlledScreen {

    private static final Logger LOGGER = Logger.getLogger(ChatGeneralScreenXController.class);

    /** The stack. */
    @FXML
    private StackPane stack;

    /** The user list. */
    @FXML
    private ListView<UserWrapper> userList;

    /** The general chat button. */
    @FXML
    private Button generalChatButton;

    /** The general conversation content. */
    @FXML
    private AnchorPane generalConversationContent;

    /** The private conversation content. */
    @FXML
    private AnchorPane privateConversationContent;

    /** The users. */
    private ObservableList<UserWrapper> users;
    // = FXCollections.observableArrayList(new HashSet<UserWrapper>());

    /** The selected. */
    private PanelType selected;

    /** The general conversation controller. */
    private GeneralConversationController generalConversationController;

    /** The private conversation controller. */
    private PrivateConversationController privateConversationController;

    /** The panels map. */
    private final Map<PanelType, AnchorPane> panelsMap = new HashMap<>();

    /** The panels cont map. */
    private final Map<PanelType, InnerController> panelsContMap = new HashMap<>();

    /** The screens controller. */
    @SuppressWarnings("unused")
    private ScreensController screensController;

    /** The onservice. */
    private OnLineUsersService onservice;

    /** The private conv notificable. */
    private NotificableService privateConvNotificable;

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.screens.ControlledScreen#enable()
     */
    /**
     * Enable.
     */
    @Override
    public void enable() {
        setSetPainel(PanelType.GENERAL_CONVERSATION_CONTENT);
        this.onservice.start();

        this.privateConvNotificable = new PrivateMessagesService(this.users, Router.getInstance(), DesktopSessionManager.getUser());
        this.privateConvNotificable.start();

    }

    /**
     * On select user.
     * @param event
     *            the event
     */
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
     * On selected user change.
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     */
    public void onSelectedUserChange(final UserWrapper oldValue, final UserWrapper newValue) {
        final int index = this.users.indexOf(newValue);
        if (index >= 0) {
            final UserWrapper userWrapper = this.users.get(index);
            if (userWrapper != null && !DesktopSessionManager.getUser().equals(userWrapper.getUser())) {
                setSetPainel(PanelType.PRIVATE_CONVERSATION_CONTENT, newValue);
            }
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

        this.users = FXCollections.observableArrayList(new Callback<UserWrapper, Observable[]>() {
            @Override
            public Observable[] call(final UserWrapper arg0) {
                return new Observable[] {arg0.getWaitingNotificationProperty() };
            }
        });
        // FXCollections.observableArrayList(new HashSet<UserWrapper>());

        this.onservice = new OnLineUsersService(this.users);

        this.userList.setCellFactory(new Callback<ListView<UserWrapper>, ListCell<UserWrapper>>() {
            @Override
            public ListCell<UserWrapper> call(final ListView<UserWrapper> p) {
                final ListCell<UserWrapper> cell = new UserListCell();
                return cell;
            }
        });
        this.userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserWrapper>() {
            @Override
            public void changed(final ObservableValue<? extends UserWrapper> observable, final UserWrapper oldValue, final UserWrapper newValue) {
                onSelectedUserChange(oldValue, newValue);
            }
        });

        this.userList.setItems(this.users);

        initComponents();

    }

    /**
     * Inits the components.
     */
    private void initComponents() {
        try {
            final FXMLLoader fxmlLoaderGeneral = new FXMLLoader(getClass().getResource(PanelType.GENERAL_CONVERSATION_CONTENT.getPath()));

            final AnchorPane loadScreen = (AnchorPane) fxmlLoaderGeneral.load();
            final GeneralConversationController controlledScreen = ((GeneralConversationController) fxmlLoaderGeneral.getController());
            controlledScreen.setFather(this);

            this.generalConversationContent = loadScreen;
            this.panelsMap.put(PanelType.GENERAL_CONVERSATION_CONTENT, this.generalConversationContent);
            this.generalConversationController = controlledScreen;
            this.panelsContMap.put(PanelType.GENERAL_CONVERSATION_CONTENT, this.generalConversationController);
            // -----------------------------
            final FXMLLoader fxmlLoaderPrivate = new FXMLLoader(getClass().getResource(PanelType.PRIVATE_CONVERSATION_CONTENT.getPath()));
            final AnchorPane loadScreen2 = (AnchorPane) fxmlLoaderPrivate.load();
            this.privateConversationController = ((PrivateConversationController) fxmlLoaderPrivate.getController());
            this.privateConversationController.setFather(this);
            this.privateConversationContent = loadScreen2;
            this.panelsMap.put(PanelType.PRIVATE_CONVERSATION_CONTENT, this.privateConversationContent);
            this.panelsContMap.put(PanelType.PRIVATE_CONVERSATION_CONTENT, this.privateConversationController);

            /*
             * if (this.stack != null && this.stack.getChildren() != null) {
             * for (final Node node : this.stack.getChildren()) {
             * switch (node.getId()) {
             * case PRIVATECONVERSATIONCONTENT:
             * this.privateConversationContent = (AnchorPane) node;
             * this.panelsMap.put(PanelType.PRIVATE_CONVERSATION_CONTENT, this.privateConversationContent);
             * break;
             * case GENERALCONVERSATIONCONTENT:
             * this.generalConversationContent = (AnchorPane) node;
             * this.panelsMap.put(PanelType.GENERAL_CONVERSATION_CONTENT, this.generalConversationContent);
             * break;
             * default:
             * break;
             * }
             * }
             */

            // this.stack.getChildren().removeAll(this.generalConversationContent, this.privateConversationContent);
            //
            // }
        } catch (final Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * On genaral chat click.
     * @param event
     *            the event
     */
    @FXML
    void onGenaralChatClick(final ActionEvent event) {

        switch (this.selected) {
        case GENERAL_CONVERSATION_CONTENT:
            // setSetPainel(PanelType.PRIVATE_CONVERSATION_CONTENT);
            break;
        case PRIVATE_CONVERSATION_CONTENT:
            setSetPainel(PanelType.GENERAL_CONVERSATION_CONTENT);
            break;
        default:

        }

    }

    /*
     * @FXML
     * void onSelectUser(final MouseEvent event) {
     * System.out.println("hsj");
     * }
     */

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.screens.ControlledScreen#setScreenParent(org.connect4.client.desktop.screens.
     * ScreensController)
     */
    /**
     * Sets the screen parent.
     * @param screenPage
     *            the new screen parent
     */
    @Override
    public void setScreenParent(final ScreensController screenPage) {
        this.screensController = screenPage;

    }

    /**
     * Sets the set painel.
     * @param type
     *            the type
     * @return true, if successful
     */
    public final boolean setSetPainel(final PanelType type) {
        if (this.panelsMap.containsKey(type)) {

            if (this.stack.getChildren().size() > 0) {
                this.stack.getChildren().remove(0);
            }

            this.stack.getChildren().add(this.panelsMap.get(type));

            // this.userList.setEventDispatcher(value);

            this.selected = type;
            return true;
        }

        return false;
    }

    /**
     * Sets the set painel.
     * @param type
     *            the type
     * @param userWrapper
     *            the user wrapper
     * @return true, if successful
     */
    public final boolean setSetPainel(final PanelType type, final UserWrapper userWrapper) {
        if (this.panelsMap.containsKey(type)) {

            if (this.stack.getChildren().size() > 0) {
                this.stack.getChildren().remove(0);
            }
            if (PanelType.GENERAL_CONVERSATION_CONTENT.equals(type)) {
                this.panelsContMap.get(PanelType.PRIVATE_CONVERSATION_CONTENT).setDataSource(null);
            }

            this.panelsContMap.get(type).setDataSource(userWrapper);

            this.stack.getChildren().add(this.panelsMap.get(type));
            this.selected = type;
            return true;
        }

        return false;
    }

    /**
     * The Class OnLineUsersService.
     */
    class OnLineUsersService implements Notificable {

        /** The observable user list. */
        private final ObservableList<UserWrapper> observableUserList;

        /** The notifier. */
        private final Notifier notifier;

        /** The instance. */
        private final OnLineUsersService instance;

        /** The user. */
        private User sessionUser;

        /** The cont. */
        private int cont = 0;

        /**
         * Instantiates a new on line users service.
         * @param observableUserList
         *            the observable user list
         */
        public OnLineUsersService(final ObservableList<UserWrapper> observableUserList) {
            super();
            this.observableUserList = observableUserList;
            this.notifier = Router.getInstance();
            this.instance = this;

        }

        /**
         * Gets the single instance of OnLineUsersService.
         * @return single instance of OnLineUsersService
         */
        public OnLineUsersService getInstance() {
            return this.instance;
        }

        /**
         * Gets the notifier.
         * @return the notifier
         */
        public Notifier getNotifier() {
            return this.notifier;
        }

        /**
         * Gets the observable user list.
         * @return the observable user list
         */
        public ObservableList<UserWrapper> getObservableUserList() {
            return this.observableUserList;
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
                        final UserWrapper userWrapper = new UserWrapper(user);

                        if (user != null && !getObservableUserList().contains(userWrapper)) {
                            userWrapper.setConversation(new Conversation(OnLineUsersService.this.sessionUser, user));
                            getObservableUserList().add(userWrapper);
                        }
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
                    @Override
                    public void run() {
                        final User user = response.getUser();
                        final UserWrapper uw = new UserWrapper(user);
                        if (user != null && getObservableUserList().contains(uw)) {
                            getObservableUserList().remove(uw);
                        }
                    }
                });
            }
        }

        /**
         * Start.
         */
        public void start() {
            if (this.cont == 0) {
                this.notifier.subscribe(this, MessageType.ON_LINE_USERS_RESPONSE);
                final OnLineUsersRequestMessage request = new OnLineUsersRequestMessage();
                this.notifier.sendMessage(request);
            }
            this.sessionUser = DesktopSessionManager.getUser();
            this.cont++;
        }

        /**
         * Stop.
         */
        public void stop() {
            this.notifier.unSubscribe(this, MessageType.ON_LINE_USERS_RESPONSE, MessageType.NOTIFICATION_SIGN_IN, MessageType.NOTIFICATION_SIGN_OUT);
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
                        getObservableUserList().clear();

                        final List<UserWrapper> lis = new ArrayList<>();

                        for (final User u : response.getOnLineUsers()) {
                            final UserWrapper us = new UserWrapper(u);
                            us.setConversation(new Conversation(OnLineUsersService.this.sessionUser, u));

                            lis.add(us);
                        }

                        getObservableUserList().addAll(lis);
                        getNotifier().subscribe(getInstance(), MessageType.NOTIFICATION_SIGN_IN, MessageType.NOTIFICATION_SIGN_OUT);
                    }
                });
            }
        }
    }

}
