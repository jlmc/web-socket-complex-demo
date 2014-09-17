/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.connect4.client.desktop.components;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import org.connect4.client.desktop.MainApp;

/**
 * The Class Page.
 * @author Carlos
 */
public class Page extends VBox {

    /** The main. */
    protected MainApp main;

    /** The lbl titulo. */
    protected Label lblTitulo;

    /**
     * Instantiates a new page.
     * @param nomeTitulo
     *            the nome titulo
     */
    public Page(final String nomeTitulo) {
        // this.main = MainApp.getMain();
        this.lblTitulo = new Label(nomeTitulo);
        setSpacing(30);
    }

}
