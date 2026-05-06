package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.Utente;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TutorialNotesController {
    private Utente utente;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField TitoloNote;
    @FXML
    private TextArea ContenutoNote;
    @FXML
    private TextArea presentazione;

    public void initialize() {
        // 1. Applichiamo lo stile base (Sfondo bianco e senza bordi brutti)
        presentazione.setWrapText(true);
        presentazione.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-control-inner-background: white; " +
                        "-fx-background-insets: 0; " +
                        "-fx-padding: 5; " +
                        "-fx-focus-color: transparent; " +
                        "-fx-faint-focus-color: transparent;"
        );

        // 2. Rimuoviamo lo scroll appena il nodo è pronto graficamente
        Platform.runLater(() -> {
            Node scrollPane = presentazione.lookup(".scroll-pane");
            if (scrollPane instanceof ScrollPane) {
                ((ScrollPane) scrollPane).setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                ((ScrollPane) scrollPane).setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
        });
    }


    public void createFirstnote(ActionEvent actionEvent) {
        // Piccolo check per evitare NullPointerException se utente non è settato
        if (utente == null) {
            errorLabel.setText("Errore: Utente non inizializzato");
            return;
        }

        utente.setNotesUtente(TitoloNote.getText(), ContenutoNote.getText());
        errorLabel.setText(utente.showNotes());
    }
}