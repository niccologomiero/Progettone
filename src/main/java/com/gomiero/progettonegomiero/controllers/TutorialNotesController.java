package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TutorialNotesController  {
    private Utente utente;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField TitoloNote;
    @FXML
    private TextArea ContenutoNote;

    public void setUtente(Utente utente) {
        this.utente = utente;
    }


    public void createFirstnote(ActionEvent actionEvent) {
//        if (TitoloNote.getText().isEmpty()){
//            errorLabel.setText("Titolo mancante");
//            return;
//        } else if (ContenutoNote.getText().isEmpty()) {
//            errorLabel.setText("Contenuto vuoto");
//            return;
//        }
        utente.setNotesUtente(TitoloNote.getText(),ContenutoNote.getText());
        errorLabel.setText(utente.showNotes());
    }
}
