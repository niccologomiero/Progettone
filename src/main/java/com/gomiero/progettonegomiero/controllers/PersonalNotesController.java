package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.Note;
import com.gomiero.progettonegomiero.models.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class PersonalNotesController {
    private Utente utente;
    private Note tempNotes;

    @FXML
    private TextField TitoloNote;
    @FXML
    private BorderPane ContenutoNote;
    @FXML
    private Label errorLabel;

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void modifyNote(ActionEvent actionEvent) {

    }
}
