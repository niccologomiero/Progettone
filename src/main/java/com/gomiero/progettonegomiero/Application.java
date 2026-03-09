package com.gomiero.progettonegomiero;

import com.gomiero.progettonegomiero.models.Utenti;
import com.gomiero.progettonegomiero.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/gomiero/progettonegomiero/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 580);

        Controller controller = fxmlLoader.getController();
        Utenti utenti = new Utenti();
        controller.setUtenti(utenti);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}