/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */

package org.connect4.client.desktop.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.connect4.client.desktop.interfaces.IGStrut;
import org.connect4.client.desktop.interfaces.Notification;
import org.connect4.client.desktop.interfaces.NotificationCenter;

/**
 * The Class TopBar.
 * @author Carlos
 */

public class TopBar extends VBox implements IGStrut {
    // private Region spacer1, spacer2;

    /** The top bar. */
    private HBox topBar;

    /** The x offset. */
    private double xOffset = 0;

    /** The y offset. */
    private double yOffset = 0;

    /** The bt2. */
    private Button bt, bt2;

    /** The lbl titulo. */
    private Label lblTitulo;

    /** The stage. */
    private final Stage stage;

    /**
     * The Constructor.
     * @param stage
     *            the stage
     */
    public TopBar(final Stage stage) {
        this.stage = stage;
        // this.main = MainApp.getMain();
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
        this.topBar = new HBox();
        this.bt = new Button("Mostrar notificacoes");
        this.bt2 = new Button("Centro de notificações");
        this.lblTitulo = new Label("Connect Four");
        this.topBar.getChildren().addAll(this.bt, this.bt2, this.lblTitulo);
        // this.getItems().add(topBar);
        getChildren().add(this.topBar);
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#changeProperties()
     */
    @Override
    public void changeProperties() {
        setPrefHeight(60);
        setMinHeight(60);
        setMaxHeight(60);
        this.topBar.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);
    }

    /**
     * Os primeiros dois listeners servem para que seja possivel arrastar a janela, clicando na barra de topo,
     * isto porque a jaNotificationontra "uNotification e como tal não existe a barra de topo.
     */
    @Override
    public void addListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @SuppressWarnings("synthetic-access")
            @Override
            public void handle(final MouseEvent event) {
                TopBar.this.xOffset = event.getSceneX();
                TopBar.this.yOffset = event.getSceneY();
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @SuppressWarnings("synthetic-access")
            @Override
            public void handle(final MouseEvent event) {
                TopBar.this.stage.setX(event.getScreenX() - TopBar.this.xOffset);
                TopBar.this.stage.setY(event.getScreenY() - TopBar.this.yOffset);
            }
        });

        this.bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent actionEvent) {
                @SuppressWarnings("unused")
                final Notification n = new Notification();
            }
        });
        this.bt2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent actionEvent) {
                @SuppressWarnings({"synthetic-access", "unused" })
                final NotificationCenter cn = new NotificationCenter(TopBar.this.bt2);
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see org.connect4.client.desktop.interfaces.IGStrut#addId()
     */
    @Override
    public void addId() {
        setId("toolBar");
        this.lblTitulo.setId("title");
    }

}
