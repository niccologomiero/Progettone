package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.Utente;
import com.gomiero.progettonegomiero.models.Utenti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    private Utenti utenti;

    @FXML
    public void initialize(){
        utenti = new Utenti();
    }

    public void setUtenti(Utenti t) {
        this.utenti = t;
    }

    @FXML
    private VBox containerInit;
    @FXML
    private TextField Email;
    @FXML
    private PasswordField Password;
    @FXML
    private Label ShowError;

    @FXML
    protected void onSingInButton(ActionEvent actionEvent) {
        Utente t;
        int result = utenti.EsisteUtente(Email.getText(),Password.getText());
        if (result == 0 ){
            t = new Utente(Email.getText(),Password.getText());
            utenti.addUtente(t);
        }
        else {
            ShowError.setText("Utente già esistente");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/home-view.fxml"
            ));
            Parent root = loader.load();

            HomeController homeController = loader.getController();
            homeController.setterUtente(t,Email.getText());

            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLogInButton(ActionEvent actionEvent) {
        int result = utenti.ControlloUtente(Email.getText(),Password.getText());
        if (result == 0 ) ShowError.setText("Utente trovato");
        if (result == 1) ShowError.setText("Password sbagliata");
        if (result == 2) ShowError.setText("Utente non trovato");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home-view.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            homeController.setterUtente(new Utente(Email.getText(),Password.getText()),Email.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}