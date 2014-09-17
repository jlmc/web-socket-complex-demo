package org.connect4.services.dal.services;

import java.util.List;

import org.connect4.entities.User;
import org.connect4.services.dal.exeptions.StoredServicesExeption;

/**
 * The Interface UserService.
 */
public interface UserService {

    /**
     * Save user.
     * @param user
     *            the user
     * @return the list
     * @throws StoredServicesExeption
     *             the stored services exeption
     */
    List<User> saveUser(User... user) throws StoredServicesExeption;

    /**
     * Gets the list.
     * @param pageNumber
     *            the page number
     * @param pageSize
     *            the page size
     * @return the list
     */
    List<User> getList(int pageNumber, int pageSize);

    /**
     * Gets the top.
     * @param top
     *            the top
     * @return the top
     */
    List<User> getTop(int top);

    /**
     * Gets the by username.
     * @param username
     *            the username
     * @return the by username
     * @throws StoredServicesExeption
     *             the stored services exeption
     */
    User getByUsername(String username) throws StoredServicesExeption;

    /**
     * Update user.
     * @param user
     *            the user
     * @return the user
     * @throws StoredServicesExeption
     *             the stored services exeption
     */
    List<User> updateUser(User... user) throws StoredServicesExeption;

}
