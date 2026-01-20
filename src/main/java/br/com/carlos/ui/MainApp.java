package br.com.carlos.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Label titulo = new Label("LeMigrador - Visualização do Banco de Dados");
        titulo.setStyle("-fx-font-size: 18px; -fx-padding: 10;");

        BorderPane root = new BorderPane();
        root.setTop(titulo);

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("LeMigrador");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
