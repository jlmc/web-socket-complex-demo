package org.connect4.client.desktop.controllers;

import org.connect4.client.desktop.models.UserWrapper;

/**
 * The Interface InnerController.
 */
public interface InnerController {

    /**
     * Sets the father.
     * @param father
     *            the new father
     */
    void setFather(ChatGeneralScreenXController father);

    /**
     * Sets the data source.
     * @param userWrapper
     *            the new data source
     */
    void setDataSource(UserWrapper userWrapper);
}
