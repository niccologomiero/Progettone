package com.gomiero.progettonegomiero.classi;

import javafx.fxml.FXML;

import java.util.ArrayList;

public class HomeController {
    private Utenti utenti;
    public void setUtenti(Utenti utenti) {
        this.utenti= utenti;
        navigateTo("home-view.fxml");
    }
    public void navigateTo(String path){
        
    }
}
