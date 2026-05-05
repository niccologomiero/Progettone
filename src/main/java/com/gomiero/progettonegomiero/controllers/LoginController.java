package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.Utente;
import com.gomiero.progettonegomiero.models.DataBase;
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

public class LoginController {
    private DataBase dataBase;
    private Utente utente;
    @FXML
    public void initialize(){
        dataBase = new DataBase();
    }

    public void setUtenti(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    @FXML
    private VBox containerInit;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label ShowError;

    @FXML
    protected void onSingInButton(ActionEvent actionEvent) {
        if (dataBase.isUsernameDisponibile(usernameField.getText())){
            utente = new Utente(usernameField.getText(), passwordField.getText());
            dataBase.registraUtente(utente);
            dataBase.setUtenteLogged(utente);
        }
        else {
            ShowError.setText("Utente già esistente");
            return;
        }
       changeScenario();
    }

    public void onLogInButton(ActionEvent actionEvent) {
        int result = dataBase.controlloUtente(usernameField.getText(), passwordField.getText());
        if (result == 0 ){
            ShowError.setText("Utente trovato");
        }
        if (result == 1){
            ShowError.setText("passwordField sbagliata");
        }
        if (result == 2){
            ShowError.setText("Utente non trovato");
        }
        utente = dataBase.getUtente(usernameField.getText());
        dataBase.setUtenteLogged(utente);
    }
    //deprecato da gestire meglio il passaggio degli utenti
    public void changeScenario() {

        if (utente.isFormSetted()){
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource(
                       "/com/gomiero/progettonegomiero/views/home-view.fxml"
               ));
               Parent root = loader.load();

               HomeController homeController = loader.getController();

               Stage stage = (Stage) containerInit.getScene().getWindow();
               stage.getScene().setRoot(root);
           } catch (IOException e){
               throw new RuntimeException(e);
           }
        }
        else {
            try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/form-view.fxml"
            ));
            Parent root = loader.load();

            FormController formController = loader.getController();
            formController.setterUtente(utente);

            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

            } catch (IOException e) {
            throw new RuntimeException(e);
            }
        }
    }
}