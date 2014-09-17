package org.connect4.services.dal.daos.impl;

import org.connect4.services.dal.daos.DAOFactory;
import org.connect4.services.dal.daos.UserDao;
import org.connect4.services.dal.daos.impl.hibernate.GenericHibernateDAO;
import org.connect4.services.dal.daos.impl.hibernate.SessionFactoryUtils;
import org.connect4.services.dal.daos.impl.hibernate.UserDaoHibernate;
import org.hibernate.Session;

/**
 * The Class HibernateDAOFactory.
 */
public class HibernateDAOFactory extends DAOFactory {

    /*
     * (non-Javadoc)
     * @see org.xine.das.dal.daos.DAOFactory#getUserDao()
     */
    @Override
    public UserDao getUserDao() {
        return (UserDao) instantiateDAO(UserDaoHibernate.class);
    }

    /**
     * Instantiate dao.
     * @param daoClass
     *            the dao class
     * @return the generic hibernate dao
     */
    @SuppressWarnings({"static-method" })
    private GenericHibernateDAO<?, ?> instantiateDAO(final Class<?> daoClass) {
        try {
            @SuppressWarnings("rawtypes")
            final GenericHibernateDAO dao = (GenericHibernateDAO) daoClass.newInstance();
            dao.setSession(getCurrentSession());
            return dao;
        } catch (final Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

    /**
     * Gets the current session.
     * @return the current session
     */
    private static Session getCurrentSession() {
        return SessionFactoryUtils.getCurrentSession();
    }

}
