/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.connect4.client.desktop.actions;

import javafx.event.Event;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.apache.log4j.Logger;
import org.connect4.client.desktop.components.LoginPage;
import org.connect4.client.endpoint.Notificable;
import org.connect4.client.endpoint.Notifier;
import org.connect4.client.endpoint.Router;
import org.connect4.messages.LoginRequestMessage;
import org.connect4.messages.LoginResponseMessage;
import org.connect4.messages.Message;
import org.connect4.messages.MessageType;

/**
 * The Class loginAction.
 * @author Administrator
 */
public class loginAction extends Action implements Notificable {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(loginAction.class); // LoggerFactory.getLogger(loginAction.class);

    /** The Notifier. */
    private final Notifier notifier = Router.getInstance();

    /** The main. */
    // private MainApp main;

    /** The tt. */
    KeyEvent tt;

    /** The login page. */
    LoginPage loginPage;

    /**
     * Instantiates a new login action.
     * @param loginPage
     *            the login page
     */
    public loginAction(final LoginPage loginPage) {
        // main = MainApp.getMain();
        this.loginPage = loginPage;

    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.actions.Action#handle(javafx.event.Event)
     */
    @Override
    public void handle(final Event t) {
        this.tt = (KeyEvent) t;

        if (this.tt.getCode() == KeyCode.ENTER) {
            final String user = this.loginPage.getTfUsername().getText();
            final String password = this.loginPage.getPfPassword().getText();
            LOGGER.info("user: " + user + " password: " + password);
            // setPressed(tt.getEventType() == tt.KEY_PRESSED);
            if (user.length() > 0 && password.length() > 0) {
                final LoginRequestMessage request = new LoginRequestMessage();
                request.setUsername(user);
                request.setPassword(password);

                this.notifier.subscribe(this, MessageType.LOGIN_RESPONSE);
                this.notifier.sendMessage(request);
            }
            this.tt.consume();
        }
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

                switch (response.getStatus()) {
                case OK:
                    LOGGER.info("Login Ok.");
                    // this.main.changeWindowInUpdatesFunction("MainChat");
                    break;
                case WRONG_PASSWORD:
                    LOGGER.info("Wrong password for the indicated Username.");
                    break;

                /** The nonexistent username. */
                case NONEXISTENT_USERNAME:
                    LOGGER.info("Username don't exist on the system.");

                    break;
                case INTERNAL_ERROR:
                    LOGGER.info("Internal error on the server.");

                    break;
                default:
                    break;
                }
            }

        } catch (final Exception e) {
            System.out.println("" + e.toString());
        }

    }

}
