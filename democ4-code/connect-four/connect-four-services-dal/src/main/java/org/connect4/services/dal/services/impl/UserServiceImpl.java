package org.connect4.services.dal.services.impl;

import java.util.LinkedList;
import java.util.List;

import org.connect4.entities.User;
import org.connect4.services.dal.daos.UserDao;
import org.connect4.services.dal.entites.ModelUser;
import org.connect4.services.dal.exeptions.StoredServicesExeption;
import org.connect4.services.dal.exeptions.StoredServicesExeption.StoredServicesExeptionType;
import org.connect4.services.dal.services.UserService;
import org.connect4.utils.functions.Preconditions;
import org.hibernate.exception.ConstraintViolationException;

/**
 * The Class UserServiceImpl.
 */
public class UserServiceImpl extends GenericService implements UserService {

    /*
     * (non-Javadoc)
     * @see org.connect4.services.dal.services.UserService#saveUser(org.connect4.services.dal.entites.ModelUser)
     */
    @Override
    public List<User> saveUser(final User... users) throws StoredServicesExeption {
        final List<User> rusers = new LinkedList<>();
        final UserDao userDao = this.factory.getUserDao();
        try {
            Preconditions.checkNotNull(users, "Can't save null Entitie.");

            userDao.beginTransaction();

            for (final User u : users) {

                rusers.add(MapperUtil.fromBase(userDao.makePersistent(MapperUtil.toBase(u))));
            }

            userDao.commitTransaction();

            return rusers;
        } catch (final ConstraintViolationException e) {
            userDao.rollbackTransaction();

            throw new StoredServicesExeption(users, StoredServicesExeptionType.USER_ALREADY_IN_USE);

        } catch (final Exception e) {
            userDao.rollbackTransaction();

            throw new StoredServicesExeption(users, StoredServicesExeptionType.UNHANDLED_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.services.dal.services.UserService#getList(int, int)
     */
    @Override
    public List<User> getList(final int pageNumber, final int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.services.dal.services.UserService#getTop(int)
     */
    @Override
    public List<User> getTop(final int top) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.services.dal.services.UserService#updateUser(org.connect4.services.dal.entites.ModelUser)
     */
    @Override
    public List<User> updateUser(final User... users) throws StoredServicesExeption {
        final List<User> rusers = new LinkedList<>();
        final UserDao userDao = this.factory.getUserDao();
        try {
            Preconditions.checkNotNull(users, "Can't save null Entitie.");

            userDao.beginTransaction();

            for (final User u : users) {

                rusers.add(MapperUtil.fromBase(userDao.makePersistent(MapperUtil.toBase(u))));
            }

            userDao.commitTransaction();

            return rusers;
        } catch (final ConstraintViolationException e) {
            userDao.rollbackTransaction();

            throw new StoredServicesExeption(users, StoredServicesExeptionType.USER_ALREADY_IN_USE);

        } catch (final Exception e) {
            userDao.rollbackTransaction();

            throw new StoredServicesExeption(users, StoredServicesExeptionType.UNHANDLED_ERROR);
        }
    }

    @Override
    public User getByUsername(final String username) throws StoredServicesExeption {
        ModelUser ruser = null;
        final UserDao userDao = this.factory.getUserDao();
        try {

            userDao.beginTransaction();

            ruser = userDao.getByUserName(String.valueOf(username).toLowerCase());

            userDao.commitTransaction();

            return MapperUtil.fromBase(ruser);

        } catch (final Exception e) {
            userDao.rollbackTransaction();

            throw new StoredServicesExeption(username, StoredServicesExeptionType.UNHANDLED_ERROR);
        }
    }

}
