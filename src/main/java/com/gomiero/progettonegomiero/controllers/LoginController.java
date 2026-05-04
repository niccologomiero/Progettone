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

    @FXML
    public void initialize(){
        dataBase = new DataBase();
    }

    public void setUtenti(DataBase t) {
        this.dataBase = t;
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
        Utente t;
        int result = dataBase.EsisteUtente(usernameField.getText());
        if (result == 0 ){
            t = new Utente(usernameField.getText(), passwordField.getText());
//            utenti.addUtente(t);
        }
        else {
            ShowError.setText("Utente già esistente");
            return;
        }
       changeScenario(t);
    }

    public void onLogInButton(ActionEvent actionEvent) {
        int result = dataBase.ControlloUtente(usernameField.getText(), passwordField.getText());
        if (result == 0 ){
            ShowError.setText("Utente trovato");
            
        }
        if (result == 1){
            ShowError.setText("passwordField sbagliata");
        }
        if (result == 2){
            ShowError.setText("Utente non trovato");
        }
        Utente utente = dataBase.getUtente(usernameField.getText());
        changeScenario(utente);
    }
    public void changeScenario(Utente utente) {
        if (utente.isFormSetted()){
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource(
                       "/com/gomiero/progettonegomiero/views/home-view.fxml"
               ));
               Parent root = loader.load();

               HomeController homeController = loader.getController();
               homeController.setterUtente(utente);

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