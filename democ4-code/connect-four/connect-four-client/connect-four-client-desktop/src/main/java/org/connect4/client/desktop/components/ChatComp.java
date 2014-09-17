/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.connect4.client.desktop.components;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import org.connect4.client.desktop.interfaces.IGStrut;

/**
 * The Class ChatComp.
 * @author Carlos
 */
public class ChatComp extends VBox implements IGStrut {

    /** The lbl titulo. */
    private Label lblTitulo;

    /** The l pessoa chat comp. */
    private List<PersonChatComp> lPessoaChatComp;

    /**
     * The Constructor.
     */
    public ChatComp() {
        initDatos();
        changeProperties();
        addListeners();
        addId();
    }

    /**
     * Adds the person chat comp.
     * @param nome
     *            the nome
     * @param idPessoa
     *            the id pessoa
     */
    public void addPersonChatComp(final String nome, final int idPessoa) {
        this.lPessoaChatComp.add(new PersonChatComp(nome, idPessoa));
        getChildren().add(this.lPessoaChatComp.get(this.lPessoaChatComp.size() - 1));
    }

    /**
     * Removes the person chat comp.
     * @param idPessoa
     *            the id pessoa
     * @return true, if removes the person chat comp
     */
    public boolean removePersonChatComp(final int idPessoa) {
        for (int i = 0; i < this.lPessoaChatComp.size(); i++) {
            if (this.lPessoaChatComp.get(i).getIdPerson() == idPessoa) {
                getChildren().remove(i + 1);
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#initDatos()
     */
    @Override
    public void initDatos() {
        this.lblTitulo = new Label("General");
        this.lPessoaChatComp = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#changeProperties()
     */
    @Override
    public void changeProperties() {
        this.lblTitulo.setPrefWidth(200);
        setSpacing(10);
        getChildren().addAll(this.lblTitulo);
        setAlignment(Pos.TOP_LEFT);
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#addListeners()
     */
    @Override
    public void addListeners() {
        // nothing eat
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#addId()
     */
    @Override
    public void addId() {
        this.lblTitulo.setId("loginLabel");
        setId("box");
    }

}
