/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.connect4.client.desktop.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import org.connect4.client.desktop.interfaces.IGStrut;

/**
 * The Class PersonChatComp.
 * @author Carlos
 */
public class PersonChatComp extends HBox implements IGStrut {

    /** The lbl name. */
    private Label lblName;

    /** The bt invite. */
    private Button btInvite;

    /** The id person. */
    private int idPerson;

    /** The name. */
    private String name;

    /** The iv. */
    private ImageView iv;

    /**
     * The Constructor.
     * @param name
     *            the name
     * @param idPerson
     *            the id person
     */
    public PersonChatComp(final String name, final int idPerson) {
        this.name = name;
        this.idPerson = idPerson;
        initDatos();
        changeProperties();
        addListeners();
        addId();
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#initDatos()
     */
    @Override
    public void initDatos() {
        this.lblName = new Label(this.name);
        this.btInvite = new Button();
        this.iv = new ImageView(new Image(getClass().getResourceAsStream("/images/IButton.png")));

    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#changeProperties()
     */
    @Override
    public void changeProperties() {
        setSpacing(5);
        /*
         * btInvite.setPrefHeight(20);
         * btInvite.setMaxHeight(20);
         * btInvite.setMinHeight(20);
         */
        this.iv.setFitHeight(30);
        this.iv.setFitWidth(30);
        this.btInvite.setGraphic(this.iv);
        setAlignment(Pos.CENTER);
        getChildren().addAll(this.lblName, this.btInvite);

    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#addListeners()
     */
    @Override
    public void addListeners() {
        // nothing yet
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#addId()
     */
    @Override
    public void addId() {
        this.btInvite.setId("bt");

    }

    /**
     * Gets the lbl nome.
     * @return the lbl nome
     */
    public Label getLblNome() {
        return this.lblName;
    }

    /**
     * Sets the lbl nome.
     * @param lblNome
     *            the lbl nome
     */
    public void setLblNome(final Label lblNome) {
        this.lblName = lblNome;
    }

    /**
     * Gets the bt invite.
     * @return the bt invite
     */
    public Button getBtInvite() {
        return this.btInvite;
    }

    /**
     * Sets the bt invite.
     * @param btInvite
     *            the bt invite
     */
    public void setBtInvite(final Button btInvite) {
        this.btInvite = btInvite;
    }

    /**
     * Gets the id person.
     * @return the id person
     */
    public int getIdPerson() {
        return this.idPerson;
    }

    /**
     * Sets the id person.
     * @param idPerson
     *            the id person
     */
    public void setIdPerson(final int idPerson) {
        this.idPerson = idPerson;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name.
     * @param name
     *            the name
     */
    public void setName(final String name) {
        this.name = name;
    }
}
