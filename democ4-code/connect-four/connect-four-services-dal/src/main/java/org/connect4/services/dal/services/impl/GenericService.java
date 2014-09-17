package org.connect4.services.dal.services.impl;

import org.connect4.services.dal.daos.DAOFactory;

/**
 * The Class GenericService.
 */
public abstract class GenericService {

    /** The factory. */
    protected final DAOFactory factory = DAOFactory.instance(DAOFactory.HIBERNATE);

}
