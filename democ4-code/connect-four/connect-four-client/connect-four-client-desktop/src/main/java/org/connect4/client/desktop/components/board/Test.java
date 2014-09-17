package org.connect4.client.desktop.components.board;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Connect Four");
        primaryStage.setResizable(true);

        final Board root = new Board();

        final Scene scene = new Scene(root, 750, 680, true);
        scene.setFill(Color.BLACK);
        scene.getStylesheets().add("net/glyphsoft/styles.css");

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }

}
