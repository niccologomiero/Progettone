package com.gomiero.progettonegomiero;

import com.gomiero.progettonegomiero.classi.Utenti;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 580);

        controller controller = fxmlLoader.getController();
        Utenti utenti = new Utenti();
        controller.setUtenti(utenti);


        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}