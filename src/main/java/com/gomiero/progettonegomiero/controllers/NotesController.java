package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NotesController {

    private Utente utente;
    private String tempNickName;

    public void setterUtente(Utente t, String nickName) {
        this.utente = t;
        this.tempNickName = nickName;
    }
    @FXML
    private BorderPane containerInit;

    public void ReturnPage(ActionEvent actionEvent) {
        System.out.println("ci sono");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/home-view.fxml"
            ));
            Parent root = loader.load();

            HomeController homeController = loader.getController();
            homeController.setterUtente(utente,tempNickName);
            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
