package org.connect4.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.connect4.messages.Message;

import com.google.gson.Gson;

/**
 * The Class JsonMessageEncoder.
 */
public class JsonMessageEncoder implements Encoder.Text<Message> {

    /*
     * (non-Javadoc)
     * @see javax.websocket.Encoder#init(javax.websocket.EndpointConfig)
     */
    @Override
    public void init(final EndpointConfig config) {
        // Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see javax.websocket.Encoder#destroy()
     */
    @Override
    public void destroy() {
        // Auto-generated method stub

    }

    /**
     * Alternative encode.
     * @param message
     *            the message
     * @return the string
     * @throws EncodeException
     *             the encode exception
     */
    @Override
    public String encode(final Message message) throws EncodeException {
        try {
            final Gson gson = new Gson();

            // convert java object to JSON format,
            // and returned as JSON formatted string
            final String json = gson.toJson(message);

            return json;
        } catch (final Exception e) {
            throw new EncodeException(message, e.getMessage());
        }
    }

}
