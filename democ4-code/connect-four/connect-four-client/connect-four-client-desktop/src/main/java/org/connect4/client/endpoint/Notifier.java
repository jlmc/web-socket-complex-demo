package org.connect4.client.endpoint;

import java.io.IOException;
import java.net.URI;

import javax.websocket.DeploymentException;

import org.connect4.messages.Message;
import org.connect4.messages.MessageType;

/**
 * The Interface Notifier.
 */
public interface Notifier {

    /**
     * Subscribe.
     * @param notifiable
     *            the notifiable
     * @param messageTypes
     *            the message types
     */
    void subscribe(Notificable notifiable, MessageType... messageTypes);

    /**
     * Un subscribe.
     * @param notifiable
     *            the notifiable
     * @param messageTypes
     *            the message types
     */
    void unSubscribe(Notificable notifiable, MessageType... messageTypes);

    /**
     * Notify.
     * @param message
     *            the message
     */
    void notify(Message message);

    /**
     * Send message, using the connector.
     * @param message
     *            the message
     * @return true if the message was sent by the connector without problems, otherwise returns false.
     */
    boolean sendMessage(Message message);

    /**
     * Connect.
     * @param uri
     *            the uri
     * @throws DeploymentException
     *             the deployment exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    void connect(final URI uri) throws DeploymentException, IOException;

    /**
     * Disconnect.
     */
    void disconnect();

}
