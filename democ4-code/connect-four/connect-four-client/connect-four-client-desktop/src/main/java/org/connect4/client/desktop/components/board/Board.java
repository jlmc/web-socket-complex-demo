package org.connect4.client.desktop.components.board;

import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * The Class Board.
 */
public class Board extends BorderPane {

    /**
     * @param args
     *            the command line arguments
     */

    private final SimpleObjectProperty<Color> playerColorProperty = new SimpleObjectProperty<Color>(Color.RED);
    private int r;
    private int c;

    /** The Constant COLS_NUM. */
    public static final int COLS_NUM = 0x7;

    /** The Constant ROWS_NUM. */
    public static final int ROWS_NUM = 0x7;

    private final GridPane gridpane;

    final Button addCellButton;

    public Board() {
        super();

        this.gridpane = new GridPane();
        this.addCellButton = new Button("Add Grids");

        this.gridpane.setTranslateY(20);
        this.gridpane.setAlignment(Pos.CENTER);

        this.gridpane.getColumnConstraints().addAll(new ColumnConstraints(100, 100, Double.MAX_VALUE), new ColumnConstraints(100, 100, Double.MAX_VALUE), new ColumnConstraints(100, 100, Double.MAX_VALUE), new ColumnConstraints(100, 100, Double.MAX_VALUE));
        this.gridpane.getRowConstraints().addAll(new RowConstraints(100, 100, Double.MAX_VALUE), new RowConstraints(100, 100, Double.MAX_VALUE), new RowConstraints(100, 100, Double.MAX_VALUE), new RowConstraints(100, 100, Double.MAX_VALUE));

        createGrids(this.gridpane);

        setCenter(this.gridpane);

        final DropShadow effect = new DropShadow();
        effect.setColor(Color.BLUE);
        this.addCellButton.setEffect(effect);

        this.addCellButton.setTranslateY(10);
        this.addCellButton.setTranslateX(10);

        setTop(this.addCellButton);

        this.addCellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent arg0) {
                addGrid(Board.this.gridpane);
            }

        });
    }

    // Add Column and Row
    private void addGrid(final GridPane gridpane) {
        gridpane.getColumnConstraints().addAll(new ColumnConstraints(100, 100, Double.MAX_VALUE));
        gridpane.getRowConstraints().addAll(new RowConstraints(100, 100, Double.MAX_VALUE));
        createGrids(gridpane);
    }

    // Create Grids
    private void createGrids(final GridPane gridpane) {
        gridpane.getChildren().clear();
        for (this.r = 0; this.r < gridpane.getColumnConstraints().size(); this.r++) {
            for (this.c = 0; this.c < gridpane.getColumnConstraints().size(); this.c++) {

                final Rectangle rect = new Rectangle(100, 100);
                final Circle circ = new Circle(47);
                circ.centerXProperty().set(50);
                circ.centerYProperty().set(50);
                final Shape cell = Path.subtract(rect, circ);
                cell.setFill(Color.BLUE);
                cell.setStroke(Color.BLUE);
                cell.setOpacity(.8);
                final DropShadow effect = new DropShadow();
                effect.setSpread(.2);
                effect.setRadius(25);
                effect.setColor(Color.BLUE);
                cell.setEffect(effect);

                final Circle diskPreview = new Circle(40);
                diskPreview.setOpacity(.5);
                diskPreview.setFill(Color.TRANSPARENT);

                diskPreview.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent arg0) {
                        diskPreview.setFill(Color.WHITE);
                        if (Board.this.playerColorProperty.get() == Color.RED) {
                            diskPreview.setFill(Color.RED);
                        } else {
                            diskPreview.setFill(Color.YELLOW);
                        }
                    }
                });

                diskPreview.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent arg0) {
                        diskPreview.setFill(Color.TRANSPARENT);
                    }
                });

                final Circle disk = new Circle(40);
                disk.fillProperty().bind(this.playerColorProperty);
                disk.setOpacity(.5);
                disk.setTranslateY(-(100 * (this.r + 1)));

                final TranslateTransition translateTranstion = new TranslateTransition(Duration.millis(300), disk);

                disk.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent arg0) {
                        diskPreview.setFill(Color.WHITE);
                        if (Board.this.playerColorProperty.get() == Color.RED) {
                            diskPreview.setFill(Color.RED);
                        } else {
                            diskPreview.setFill(Color.YELLOW);
                        }
                    }
                });

                disk.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent arg0) {
                        diskPreview.setFill(Color.TRANSPARENT);
                    }
                });

                disk.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent arg0) {
                        if (disk.getTranslateY() != 0) {
                            translateTranstion.setToY(0);
                            translateTranstion.play();
                            if (Board.this.playerColorProperty.get() == Color.RED) {
                                Board.this.playerColorProperty.set(Color.YELLOW);
                                disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.RED));
                            } else {
                                Board.this.playerColorProperty.set(Color.RED);
                                disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.YELLOW));
                            }
                        }
                    }
                });

                diskPreview.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent arg0) {
                        if (disk.getTranslateY() != 0) {
                            translateTranstion.setToY(0);
                            translateTranstion.play();
                            if (Board.this.playerColorProperty.get() == Color.RED) {
                                Board.this.playerColorProperty.set(Color.YELLOW);
                                disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.RED));
                            } else {
                                Board.this.playerColorProperty.set(Color.RED);
                                disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.YELLOW));
                            }
                        }
                    }
                });

                final StackPane stack = new StackPane();

                stack.getChildren().addAll(cell, diskPreview, disk);

                gridpane.add(stack, this.c, this.r);

                if (this.r == gridpane.getColumnConstraints().size() - 1) {
                    stack.setEffect(new Reflection());
                }
            }

        }
    }

}
