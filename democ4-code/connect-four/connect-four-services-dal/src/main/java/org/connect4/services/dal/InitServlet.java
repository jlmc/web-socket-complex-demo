package org.connect4.services.dal;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import org.connect4.services.dal.daos.DAOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class InitServlet.
 */
@WebServlet(name = "InitServlet")
public class InitServlet extends GenericServlet {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(InitServlet.class);

    /** The dao. */
    private final DAOFactory daoFactory = DAOFactory.instance(DAOFactory.HIBERNATE);

    /*
     * (non-Javadoc)
     * @see javax.servlet.GenericServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    public void service(final ServletRequest arg0, final ServletResponse arg1) throws ServletException, IOException {
        // Nothing
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        super.init();

        try {

            this.daoFactory.getUserDao();
        } catch (final Exception e) {
            LOGGER.error("framework modules Could not initiaze the Hibernate Session Factory", e);
            // throw new RuntimeException("framework modules Could not initiaze the Hibernate Session Factory", e);
        } /*
           * finally {
           * // empty
           * try {
           * if (session != null) {
           * // session.flush();
           * session.close();
           * LOGGER.info("framework modules Hibernate initialization complete.");
           * }
           * } catch (final HibernateException e) {
           * LOGGER.warn("framework modules Could not finish the Hibernate session after initializing", e);
           * }
           * }
           */

    }
}
