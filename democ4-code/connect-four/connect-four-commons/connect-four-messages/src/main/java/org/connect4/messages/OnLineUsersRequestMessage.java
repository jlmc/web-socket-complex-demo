package org.connect4.messages;


/**
 * The Class OnLineUsersRequestMessage.
 */
public class OnLineUsersRequestMessage extends Message {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new on line users request message.
     */
    public OnLineUsersRequestMessage() {
        super(MessageType.ON_LINE_USERS_REQUEST);
    }

}
