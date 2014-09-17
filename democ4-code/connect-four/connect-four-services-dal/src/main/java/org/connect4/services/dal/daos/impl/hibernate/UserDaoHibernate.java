package org.connect4.services.dal.daos.impl.hibernate;

import org.connect4.services.dal.daos.UserDao;
import org.connect4.services.dal.entites.ModelUser;

/**
 * The Class UserDaoHibernate.
 */
public class UserDaoHibernate extends GenericHibernateDAO<ModelUser, Long> implements UserDao {

    @Override
    public ModelUser getByUserName(final String username) {
        return (ModelUser) super.getSession().createQuery("From ModelUser u where u.credentials.username = :value")
                .setParameter("value", username).setMaxResults(1).uniqueResult();

    }

}
