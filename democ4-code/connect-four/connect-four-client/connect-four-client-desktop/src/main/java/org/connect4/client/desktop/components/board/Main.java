package org.connect4.client.desktop.components.board;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author mark_anro
 */
public class Main extends Application {

    /**
     * @param args
     *            the command line arguments
     */

    private final SimpleObjectProperty<Color> playerColorProperty = new SimpleObjectProperty<Color>(Color.RED);
    private int r;
    private int c;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        final BorderPane root = new BorderPane();
        final GridPane gridpane = new GridPane();
        primaryStage.setTitle("JavaFX Connect Four");
        primaryStage.setResizable(true);

        final Button addCellButton = new Button("Add Grids");

        final Scene scene = new Scene(root, 750, 680, true);
        scene.setFill(Color.BLACK);
        scene.getStylesheets().add("net/glyphsoft/styles.css");

        gridpane.setTranslateY(20);
        gridpane.setAlignment(Pos.CENTER);

        gridpane.getColumnConstraints().addAll(new ColumnConstraints(100, 100, Double.MAX_VALUE), new ColumnConstraints(100, 100, Double.MAX_VALUE), new ColumnConstraints(100, 100, Double.MAX_VALUE), new ColumnConstraints(100, 100, Double.MAX_VALUE));
        gridpane.getRowConstraints().addAll(new RowConstraints(100, 100, Double.MAX_VALUE), new RowConstraints(100, 100, Double.MAX_VALUE), new RowConstraints(100, 100, Double.MAX_VALUE), new RowConstraints(100, 100, Double.MAX_VALUE));

        createGrids(gridpane);

        root.setCenter(gridpane);

        final DropShadow effect = new DropShadow();
        effect.setColor(Color.BLUE);
        addCellButton.setEffect(effect);

        addCellButton.setTranslateY(10);
        addCellButton.setTranslateX(10);

        root.setTop(addCellButton);

        addCellButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent arg0) {
                addGrid(gridpane);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
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
                        if (Main.this.playerColorProperty.get() == Color.RED) {
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
                        if (Main.this.playerColorProperty.get() == Color.RED) {
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
                            if (Main.this.playerColorProperty.get() == Color.RED) {
                                Main.this.playerColorProperty.set(Color.YELLOW);
                                disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.RED));
                            } else {
                                Main.this.playerColorProperty.set(Color.RED);
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
                            if (Main.this.playerColorProperty.get() == Color.RED) {
                                Main.this.playerColorProperty.set(Color.YELLOW);
                                disk.fillProperty().bind(new SimpleObjectProperty<Color>(Color.RED));
                            } else {
                                Main.this.playerColorProperty.set(Color.RED);
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
