/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.connect4.client.desktop.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.connect4.client.desktop.interfaces.IGStrut;

/**
 * The Class MainChatPage.
 * @author Carlos
 */
public class MainChatPage extends Page implements IGStrut {

    /** The hb base. */
    private HBox hbBase;

    /** The vb chat. */
    private VBox vbChat;

    /** The tf chat. */
    private TextField tfChat;

    /** The ta area chat. */
    private TextArea taAreaChat;

    /** The chat comp. */
    private ChatComp chatComp;

    /** The lbl. */
    private Label lbl;

    /**
     * Instantiates a new main chat page.
     * @param nomeTitulo
     *            the nome titulo
     */
    public MainChatPage(final String nomeTitulo) {
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
        this.hbBase = new HBox();
        this.vbChat = new VBox();
        this.tfChat = new TextField();
        this.taAreaChat = new TextArea();
        this.chatComp = new ChatComp();
        this.lbl = new Label();
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#changeProperties()
     */
    @Override
    public void changeProperties() {

        this.taAreaChat.setPrefHeight(520);
        this.taAreaChat.setPrefWidth(950);
        this.taAreaChat.setEditable(false);
        this.vbChat.setSpacing(20);
        this.hbBase.setSpacing(20);
        this.hbBase.setAlignment(Pos.CENTER);
        this.chatComp.addPersonChatComp("Carlos", 1);
        this.vbChat.getChildren().addAll(this.taAreaChat, this.tfChat);
        this.hbBase.getChildren().addAll(this.vbChat, this.chatComp);
        getChildren().addAll(this.lbl, this.hbBase);
        setAlignment(Pos.CENTER);
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#addListeners()
     */
    @Override
    public void addListeners() {
        // nothing
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#addId()
     */
    @Override
    public void addId() {
        this.tfChat.setId("loginTextfield");
        this.taAreaChat.setId("box");

    }

}
