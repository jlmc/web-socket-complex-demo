package org.connect4.client.desktop.controllers;

/**
 * The Enum PanelType.
 */
// final String PRIVATECONVERSATIONCONTENT_FILE = "/fxml/ParticularConversation.fxml";
// final String GENERALCONVERSATIONCONTENT_FILE = "/fxml/GeneralConversation.fxml";

public enum PanelType {

    /** The general conversation content. */
    GENERAL_CONVERSATION_CONTENT("generalConversationContent", "/fxml/GeneralConversation.fxml"),

    /** The privateconversationcontent. */
    PRIVATE_CONVERSATION_CONTENT("privateConversationContent", "/fxml/ParticularConversation.fxml");

    /** The id. */
    private final String id;

    /** The path. */
    private final String path;

    /**
     * Instantiates a new panel type.
     * @param id
     *            the id
     * @param path
     *            the path
     */
    PanelType(final String id, final String path) {
        this.id = id;
        this.path = path;
    }

    /**
     * Gets the id.
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the path.
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

}
