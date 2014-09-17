/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.connect4.client.desktop.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import org.connect4.client.desktop.actions.loginAction;
import org.connect4.client.desktop.interfaces.IGStrut;

/**
 * The Class LoginPage.
 */
public class LoginPage extends Page implements IGStrut {

    /** The iv. */
    private ImageView iv;

    /** The hb login. */
    private HBox hbRegisto, hbLogin;

    /** The spacer2. */
    private Region spacer, spacer2;

    /** The lbl password. */
    private Label lblUsername, lblPassword;

    /** The tf username. */
    private TextField tfUsername;

    /** The pf password. */
    private PasswordField pfPassword;

    /**
     * Instantiates a new login page.
     * @param nomeTitulo
     *            the nome titulo
     */
    public LoginPage(final String nomeTitulo) {
        super(nomeTitulo);
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
        this.iv = new ImageView(new Image(getClass().getResourceAsStream("/images/Logo.png")));
        this.iv.setFitWidth(1150);
        this.iv.setFitHeight(350);
        this.hbRegisto = new HBox();
        this.hbLogin = new HBox();
        this.spacer = new Region();
        this.spacer2 = new Region();
        this.lblUsername = new Label("Login :");
        this.tfUsername = new TextField();
        this.pfPassword = new PasswordField();
        this.lblPassword = new Label("Password :");
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#changeProperties()
     */
    @Override
    public void changeProperties() {
        getChildren().addAll(this.spacer, this.hbRegisto, this.iv, this.hbLogin);
        setAlignment(Pos.CENTER);
        VBox.setVgrow(this.spacer, Priority.ALWAYS);
        this.spacer.setPrefHeight(60);
        this.spacer.setMinHeight(60);
        this.spacer.setMaxHeight(60);
        this.hbLogin.setAlignment(Pos.CENTER);
        HBox.setHgrow(this.spacer2, Priority.ALWAYS);
        this.spacer2.setPrefWidth(50);
        this.spacer2.setMinWidth(50);
        this.spacer2.setMaxWidth(50);

        this.hbLogin.getChildren().addAll(this.lblUsername, this.tfUsername, this.spacer2, this.lblPassword,
                this.pfPassword);
        this.lblUsername.setAlignment(Pos.CENTER);
        this.lblUsername.setPrefSize(150, 40);
        this.tfUsername.setPrefSize(400, 40);

        this.lblPassword.setAlignment(Pos.CENTER);
        this.lblPassword.setPrefSize(150, 40);
        this.pfPassword.setPrefSize(400, 40);
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#addListeners()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addListeners() {
        this.pfPassword.setOnKeyPressed(new loginAction(this));
        this.tfUsername.setOnKeyPressed(new loginAction(this));
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#addId()
     */
    @Override
    public void addId() {
        this.lblUsername.setId("loginLabel");
        this.lblPassword.setId("loginLabel");
        this.tfUsername.setId("loginTextfield");
        this.pfPassword.setId("loginTextfield");

    }

    /**
     * Gets the tf username.
     * @return the tf username
     */
    public TextField getTfUsername() {
        return this.tfUsername;
    }

    /**
     * Sets the tf username.
     * @param tfUsername
     *            the new tf username
     */
    public void setTfUsername(final TextField tfUsername) {
        this.tfUsername = tfUsername;
    }

    /**
     * Gets the pf password.
     * @return the pf password
     */
    public PasswordField getPfPassword() {
        return this.pfPassword;
    }

    /**
     * Sets the pf password.
     * @param pfPassword
     *            the new pf password
     */
    public void setPfPassword(final PasswordField pfPassword) {
        this.pfPassword = pfPassword;
    }

}
