package org.connect4.services.dal.entites;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class ModelUser.
 */
@Entity
@Table(name = "USERS")
public class ModelUser implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue
    private Long id;

    /** The credentials. */
    @Embedded
    private ModelCredentials credentials;

    /** The display name. */
    private String displayName;

    /** The total games. */
    private int totalGames;

    /** The total games won. */
    private int totalGamesWon;

    /**
     * Instantiates a new user.
     */
    public ModelUser() {
        super();
        // Auto-generated constructor stub
    }

    /**
     * Instantiates a new user.
     * @param id
     *            the id
     * @param credentials
     *            the credentials
     * @param displayName
     *            the display name
     */
    public ModelUser(final Long id, final ModelCredentials credentials, final String displayName) {
        super();
        this.id = id;
        this.credentials = credentials;
        this.displayName = displayName;
    }

    /**
     * The Constructor.
     * @param id
     *            the id
     * @param displayName
     *            the display name
     * @param totalGamesWon
     *            the total games won
     * @param totalGames
     *            the total games
     * @param credentials
     *            the credentials
     */
    public ModelUser(final Long id, final String displayName, final int totalGamesWon, final int totalGames,
            final ModelCredentials credentials) {
        this(id, displayName, credentials);
        this.totalGamesWon = totalGamesWon;
        setTotalGames(totalGames);
    }

    /**
     * Instantiates a new user.
     * @param id
     *            the id
     * @param displayName
     *            the display name
     * @param credentials
     *            the credentials
     */
    public ModelUser(final Long id, final String displayName, final ModelCredentials credentials) {
        this(id, credentials, displayName);

    }

    /**
     * Instantiates a new user.
     * @param other
     *            the other
     */
    public ModelUser(final ModelUser other) {
        this(other.id, other.displayName, other.totalGamesWon, other.totalGamesWon, other.credentials);
    }

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
        final ModelUser other = (ModelUser) obj;
        if (this.credentials == null) {
            if (other.credentials != null) {
                return false;
            }
        } else if (!this.credentials.equals(other.credentials)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the credentials.
     * @return the credentials
     */
    public ModelCredentials getCredentials() {
        return this.credentials;
    }

    /**
     * Gets the display name.
     * @return the display name
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Gets the id.
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Gets the total games.
     * @return the total games
     */
    public int getTotalGames() {
        return this.totalGames;
    }

    /**
     * Gets the total games won.
     * @return the total games won
     */
    public int getTotalGamesWon() {
        return this.totalGamesWon;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.credentials == null) ? 0 : this.credentials.hashCode());
        return result;
    }

    /**
     * Sets the credentials.
     * @param credentials
     *            the new credentials
     */
    public void setCredentials(final ModelCredentials credentials) {
        this.credentials = credentials;
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
     * Sets the id.
     * @param id
     *            the new id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Sets the total games.
     * @param totalGames
     *            the new total games
     */
    public void setTotalGames(final int totalGames) {
        this.totalGames = totalGames;
    }

    /**
     * Sets the total games won.
     * @param totalGamesWon
     *            the total games won
     */
    public void setTotalGamesWon(final int totalGamesWon) {
        this.totalGamesWon = totalGamesWon;
    }

}
