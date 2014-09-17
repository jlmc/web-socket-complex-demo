package org.connect4.services.dal.entites;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class Credentials.
 */
@Embeddable
public class ModelCredentials {

    /** The username. */
    @Column(name = "username", length = 16, nullable = false, unique = true)
    private String username;

    /** The password. */
    @Column(name = "password", length = 16, nullable = false, unique = false)
    private String password;

    /**
     * Instantiates a new model credentials.
     */
    public ModelCredentials() {
        super();
    }

    /**
     * Instantiates a new model credentials.
     * @param username
     *            the username
     * @param password
     *            the password
     */
    public ModelCredentials(final String username, final String password) {
        super();
        setUsername(username);

        this.password = password;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (this.username == null) {
            return Objects.hashCode(null);
        }
        return Objects.hashCode(String.valueOf(this.username).toLowerCase());
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
        final ModelCredentials other = (ModelCredentials) obj;
        if (this.password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!this.password.trim().equals(other.password.trim())) {
            return false;
        }
        if (this.username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!this.username.trim().equalsIgnoreCase(other.username.trim())) {
            return false;
        }
        return true;
    }

    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username.
     * @param username
     *            the new username
     */
    public void setUsername(final String username) {
        if (username != null && !username.trim().isEmpty()) {
            this.username = String.valueOf(username).toLowerCase();
        }
    }

    /**
     * Gets the password.
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     * @param password
     *            the new password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

}
