package org.connect4.services.dal.daos;

import org.connect4.services.dal.entites.ModelUser;

/**
 * The Interface UserDao.
 */
public interface UserDao extends GenericDAO<ModelUser, Long> {

    /**
     * Gets the by user name.
     * @param username
     *            the username
     * @return the by user name
     */
    ModelUser getByUserName(String username);

}
