package com.gomiero.progettonegomiero;

import com.gomiero.progettonegomiero.classi.HomeController;
import com.gomiero.progettonegomiero.classi.Utente;
import com.gomiero.progettonegomiero.classi.Utenti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class controller {
    private Utenti utenti = new Utenti();

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
        int result = utenti.EsisteUtente(Email.getText(),Password.getText());
        if (result == 0 ) utenti.addUtente(new Utente(Email.getText(),Password.getText()));
        else {
            ShowError.setText("Utente già esistente");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home-view.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            homeController.setUtenti(utenti);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLogInButton(ActionEvent actionEvent) {
        int result = utenti.ControlloUtente(Email.getText(),Password.getText());
        if (result == 0 ) ShowError.setText("Utente trovato");
        if (result == 1) ShowError.setText("Password sbagliata");
        if (result == 2) ShowError.setText("Utente non trovato");
    }
}