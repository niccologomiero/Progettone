package com.gomiero.progettonegomiero.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ProvaController {
    @FXML
    private ListView<String> miaLista;

    public void initialize() {
        ObservableList<String> dati = FXCollections.observableArrayList();

        // Genera 100 elementi in automatico
        for (int i = 1; i <= 100; i++) {
            dati.add("Utente #" + i + " - Stato: " + (i % 2 == 0 ? "Online" : "Offline"));
        }

        miaLista.setItems(dati);
    }
}
