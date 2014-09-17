package org.connect4.client.desktop.models;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.connect4.entities.Conversation;
import org.connect4.entities.User;

/**
 * The Class UserWrapper.
 */
public class UserWrapper {

    /** The user. */
    private final User user;

    /** The conversation. */
    private Conversation conversation;

    /** The history conversation. */
    private final StringBuilder historyConversation = new StringBuilder();

    /** The number ofwaiting notification. */
    // private int numberOfwaitingNotification;

    /** The waiting notification. */
    private final IntegerProperty waitingNotification = new SimpleIntegerProperty(0);

    /** The visible. */
    private boolean showing = false;

    /** The string property. */
    @SuppressWarnings("unused")
    private StringProperty stringProperty;

    /**
     * Instantiates a new user wrapper.
     * @param user
     *            the user
     */
    public UserWrapper(final User user) {
        this.user = user;

        setWaitingNotification(new Integer(0));
    }

    /**
     * Gets the conversation.
     * @return the conversation
     */
    public Conversation getConversation() {
        return this.conversation;
    }

    /**
     * Sets the conversation.
     * @param conversation
     *            the new conversation
     */
    public void setConversation(final Conversation conversation) {
        this.conversation = conversation;
    }

    /**
     * Gets the user.
     * @return the user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Gets the number ofwaiting notification.
     * @return the number ofwaiting notification
     */
    /*
     * public int getNumberOfwaitingNotification() {
     * return this.numberOfwaitingNotification; // this.numberOfwaitingNotification.get();
     * }
     */

    /**
     * Sets the number ofwaiting notification.
     * @param numberOfwaitingNotification
     *            the new number ofwaiting notification
     */
    /*
     * public void setNumberOfwaitingNotification(final int numberOfwaitingNotification) {
     * this.numberOfwaitingNotification = numberOfwaitingNotification; // .set(numberOfwaitingNotification); // =
     * // numberOfwaitingNotification;
     * }
     */

    /**
     * Have conversation.
     * @return true, if successful
     */
    public boolean haveConversation() {
        return (this.conversation != null);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.user == null) ? 0 : this.user.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserWrapper other = (UserWrapper) obj;
        if (this.user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!this.user.equals(other.user)) {
            return false;
        }
        return true;
    }

    /**
     * Adds the message.
     * @param wroter
     *            the wroter
     * @param str
     *            the str
     */
    public void appendMessage(final User wroter, final String str) {
        if (wroter != null && wroter.getUsername() != null && str != null && !str.trim().isEmpty()) {

            final String s = String.format("%s wrote:\n %s\n\n", wroter.getUsername(), str);
            this.historyConversation.append(s);

            if (!this.showing) {
                setWaitingNotification(new Integer(getWaitingNotification().intValue() + 1));
            }

            Platform.runLater(new Runnable() {

                @SuppressWarnings("synthetic-access")
                @Override
                public void run() {
                    setSomeString(UserWrapper.this.historyConversation.toString());

                }

            });

        }
    }

    /** The some string. */
    private final StringProperty someString = new SimpleStringProperty("");

    /**
     * Some string property.
     * @return the string property
     */
    public StringProperty someStringProperty() {
        return this.someString;
    }

    /**
     * Gets the some string.
     * @return the some string
     */
    public String getSomeString() {
        return someStringProperty().get();
    }

    /**
     * Sets the some string.
     * @param newString
     *            the new some string
     */
    public void setSomeString(final String newString) {
        someStringProperty().set(newString);
    }

    /**
     * Checks if is visible.
     * @return true, if is visible
     */

    public boolean isShow() {
        return this.showing;
    }

    /**
     * Sets the visible.
     * @param show
     *            the new show
     */
    public void setShow(final boolean show) {
        this.showing = show;
        setWaitingNotification(new Integer(0));
    }

    /**
     * Sets the waiting notification.
     * @param value
     *            the new waiting notification
     */
    public final void setWaitingNotification(final Integer value) {
        this.waitingNotification.setValue(value);
    }

    /**
     * Gets the waiting notification.
     * @return the waiting notification
     */
    public final Integer getWaitingNotification() {
        return this.waitingNotification.getValue();
    }

    /**
     * Gets the waiting notification property.
     * @return the waiting notification property
     */
    public IntegerProperty getWaitingNotificationProperty() {
        return this.waitingNotification;
    }

}
