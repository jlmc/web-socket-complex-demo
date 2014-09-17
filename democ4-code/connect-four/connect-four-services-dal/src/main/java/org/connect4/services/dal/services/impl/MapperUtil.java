package org.connect4.services.dal.services.impl;

import org.connect4.entities.User;
import org.connect4.services.dal.entites.ModelCredentials;
import org.connect4.services.dal.entites.ModelUser;

/**
 * The Class MapperUtil.
 */
public final class MapperUtil {

    /**
     * Instantiates a new mapper util.
     */
    private MapperUtil() {
        super();
    }

    /**
     * To base.
     * @param user
     *            the user
     * @return the model user
     */
    protected static ModelUser toBase(final User user) {
        ModelUser modelUser = null;
        if (user != null) {
            modelUser = new ModelUser();

            modelUser.setId((user.getId() <= 0L) ? null : Long.valueOf(user.getId()));
            modelUser.setCredentials(new ModelCredentials(user.getUsername(), user.getPassword()));
            modelUser.setDisplayName(user.getDisplayName());
            modelUser.setTotalGames(user.getTotalGames());
            modelUser.setTotalGamesWon(user.getTotalGamesWon());
        }
        return modelUser;
    }

    /**
     * From base.
     * @param modelUser
     *            the model user
     * @return the user
     */
    protected static User fromBase(final ModelUser modelUser) {
        User user = null;
        if (modelUser != null) {
            user = new User();
            user.setId(modelUser.getId() != null ? modelUser.getId().longValue() : 0L);

            user.setDisplayName(modelUser.getDisplayName());
            user.setTotalGames(modelUser.getTotalGames());
            user.setTotalGamesWon(modelUser.getTotalGamesWon());

            if (modelUser.getCredentials() != null) {
                user.setUsername(modelUser.getCredentials().getUsername());
                user.setPassword(modelUser.getCredentials().getPassword());
            }
        }
        return user;
    }
}
