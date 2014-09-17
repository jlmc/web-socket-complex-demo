package org.connect4.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Class User.
 */
public class User implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private long id;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /**
     * Gets the total games won.
     * @return the total games won
     */
    public int getTotalGamesWon() {
        return this.totalGamesWon;
    }

    /**
     * Sets the total games won.
     * @param totalGamesWon
     *            the total games won
     */
    public void setTotalGamesWon(final int totalGamesWon) {
        this.totalGamesWon = totalGamesWon;
    }

    /** The display name. */
    private String displayName;

    /** The total games. */
    private int totalGames;

    /** The total games won. */
    private int totalGamesWon;

    /**
     * Instantiates a new user.
     */
    public User() {
        super();
    }

    /**
     * Instantiates a new user.
     * @param id
     *            the id
     * @param username
     *            the username
     * @param displayName
     *            the display name
     */
    public User(final long id, final String username, final String displayName) {
        super();
        this.id = id;
        this.username = username;
        this.displayName = displayName;
    }

    /**
     * Instantiates a new user.
     * @param other
     *            the other
     */
    public User(final User other) {
        this(other.id, other.username, other.displayName, other.password, other.totalGamesWon, other.totalGamesWon);
    }

    /**
     * Instantiates a new user.
     * @param id
     *            the id
     * @param username
     *            the username
     * @param displayName
     *            the display name
     * @param password
     *            the password
     */
    public User(final long id, final String username, final String displayName, final String password) {
        this(id, username, displayName);
        this.password = password;
    }

    /**
     * The Constructor.
     * @param id
     *            the id
     * @param username
     *            the username
     * @param displayName
     *            the display name
     * @param password
     *            the password
     * @param totalGamesWon
     *            the total games won
     * @param totalGames
     *            the total games
     */
    public User(final long id, final String username, final String displayName, final String password,
            final int totalGamesWon, final int totalGames) {
        this(id, username, displayName, password);
        this.totalGamesWon = totalGamesWon;
        setTotalGames(totalGames);
    }

    /**
     * Gets the id.
     * @return the id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the id.
     * @param id
     *            the new id
     */
    public void setId(final long id) {
        this.id = id;
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
        this.username = username;
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

    /**
     * Gets the display name.
     * @return the display name
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Sets the display name.
     * @param displayName
     *            the new display name
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns a hash code value for the object.
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(String.valueOf(this.username).trim().toLowerCase());
    }

    /**
     * Equals.
     * @param obj
     *            the obj
     * @return true, if successful
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
        final User other = (User) obj;
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
     * Gets the total games.
     * @return the total games
     */
    public int getTotalGames() {
        return this.totalGames;
    }

    /**
     * Sets the total games.
     * @param totalGames
     *            the total games
     */
    public void setTotalGames(final int totalGames) {
        this.totalGames = totalGames;
    }

}
