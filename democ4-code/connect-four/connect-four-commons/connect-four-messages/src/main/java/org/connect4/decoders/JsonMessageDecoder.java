package org.connect4.decoders;

import java.io.StringReader;

import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.connect4.messages.ChatMessage;
import org.connect4.messages.CreateAcountRequestMessage;
import org.connect4.messages.CreateAcountResponseMessage;
import org.connect4.messages.LoginRequestMessage;
import org.connect4.messages.LoginResponseMessage;
import org.connect4.messages.Message;
import org.connect4.messages.MessageKeys;
import org.connect4.messages.MessageType;
import org.connect4.messages.NotificationSignInMessage;
import org.connect4.messages.NotificationSingOutMessage;
import org.connect4.messages.OnLineUsersRequestMessage;
import org.connect4.messages.OnLineUsersResponseMessage;
import org.connect4.messages.PrivateChatMessage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * The Class JsonMessageDecoder.
 */
public class JsonMessageDecoder implements Decoder.Text<Message> {

    /*
     * (non-Javadoc)
     * @see javax.websocket.Decoder#init(javax.websocket.EndpointConfig)
     */
    @Override
    public void init(final EndpointConfig config) {
        // Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see javax.websocket.Decoder#destroy()
     */
    @Override
    public void destroy() {
        // Auto-generated method stub
    }

    /**
     * Determine if the message can be converted into either one of Message types Instance.
     * @param s
     *            the s
     * @return true, if will decode
     */
    @Override
    public boolean willDecode(final String s) {
        try {

            // check if incomming message is valid JSON
            Json.createReader(new StringReader(s)).readObject();

            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * Alternative decode.
     * @param string
     *            the string
     * @return the message
     * @throws DecodeException
     *             the decode exception
     */
    @Override
    public Message decode(final String string) throws DecodeException {

        try {
            Message message = null;
            final Gson gson = new Gson();

            final JsonElement root = new JsonParser().parse(string);
            final String tr = root.getAsJsonObject().get(MessageKeys.TYPE).getAsString();

            final MessageType messageType = MessageType.valueOf(tr);

            switch (messageType) {
            case PUBLIC:
                message = gson.fromJson(string, ChatMessage.class);
                break;
            case LOGIN_REQUEST:
                message = gson.fromJson(string, LoginRequestMessage.class);
                break;
            case LOGIN_RESPONSE:
                message = gson.fromJson(string, LoginResponseMessage.class);
                break;
            case CREATE_ACOUNT_REQUEST:
                message = gson.fromJson(string, CreateAcountRequestMessage.class);
                break;
            case CREATE_ACOUNT_RESPONSE:
                message = gson.fromJson(string, CreateAcountResponseMessage.class);
                break;
            case ON_LINE_USERS_REQUEST:
                message = gson.fromJson(string, OnLineUsersRequestMessage.class);
                break;
            case ON_LINE_USERS_RESPONSE:
                message = gson.fromJson(string, OnLineUsersResponseMessage.class);
                break;
            case PRIVATE:
                message = gson.fromJson(string, PrivateChatMessage.class);
                break;
            case NOTIFICATION_SIGN_IN:
                message = gson.fromJson(string, NotificationSignInMessage.class);
                break;
            case NOTIFICATION_SIGN_OUT:
                message = gson.fromJson(string, NotificationSingOutMessage.class);
                break;
            default:
                break;
            }

            return message;
        } catch (final Exception e) {
            throw new DecodeException(string, e.getMessage());
        }
    }

}
