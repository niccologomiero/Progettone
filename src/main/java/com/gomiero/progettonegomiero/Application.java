package com.gomiero.progettonegomiero;

import com.gomiero.progettonegomiero.controllers.LoginController;
import com.gomiero.progettonegomiero.models.DataBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/gomiero/progettonegomiero/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 580);

        LoginController controller = fxmlLoader.getController();
        LoginController loginController = fxmlLoader.getController();
        loginController.setUtenti(DataBase.getInstance());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            DataBase.getInstance().saveDataToJSON();
        });
    }


    public static void main(String[] args) {
        launch();
    }
}