package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.DataBase;
import com.gomiero.progettonegomiero.models.GetData;
import com.gomiero.progettonegomiero.models.Utente;
import com.gomiero.progettonegomiero.models.contabilities.MeseCountability;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller per la vista Home (Dashboard).
 * Gestisce la logica di visualizzazione dei grafici (PieChart) e lo smistamento
 * della navigazione tra le diverse pagine dell'applicazione.
 */
public class HomeController implements GetData {

    // --- Campi di istanza (Modelli e Dati) ---
    private DataBase dataBase;                // Istanza del database (Singleton)
    private Utente utente;                    // L'utente attualmente loggato
    private MeseCountability meseCountability; // Oggetto per gestire i calcoli contabili del mese
    private int counterPieChart = 0;          // Stato attuale del grafico (1: Budget, 2: Medie, 3: Dettagli)

    // --- Elementi UI iniettati dal file FXML ---
    @FXML
    private ArrayList<PieChart.Data> chartData; // Nota: di solito non è @FXML se non è un elemento grafico
    @FXML
    private Label welcomeUser;        // Messaggio di benvenuto (es: "Mario's Home")
    @FXML
    public Button btn_GoLogout, btn_GoGraph, btn_GoNotes; // Pulsanti di navigazione
    @FXML
    private PieChart pieChart;        // Il componente grafico a torta
    @FXML
    private Button btn_backPiechart, btn_nextPiechart; // Pulsanti per scorrere i grafici
    @FXML
    private VBox containerInit;       // Container principale usato per ottenere la Scene/Stage

    /**
     * Implementazione dell'interfaccia GetData.
     * Recupera l'utente loggato dal Database e inizializza i riferimenti contabili.
     */
    @Override
    public void getData() {
        this.dataBase = DataBase.getInstance();
        this.utente = dataBase.getUtenteLogged();
        if (utente != null) {
            // Personalizza l'interfaccia con il nome utente
            welcomeUser.setText(utente.getUsername() + "'s Home");
            // Recupera l'oggetto per i calcoli del mese
            meseCountability = utente.getContabilities();
        }
    }

    /**
     * Metodo di inizializzazione di JavaFX. 
     * Viene eseguito dopo che il file FXML è stato caricato.
     */
    @FXML
    private void initialize() {
        getData();                // Carica i dati dell'utente
        setPieChartBudgetAmount(); // Mostra come primo grafico quello del Budget
    }

    /**
     * Configura il PieChart per mostrare il rapporto tra Entrate (Stipendio) e Uscite.
     * Stato: counterPieChart = 1.
     */
    public void setPieChartBudgetAmount() {
        pieChart.getData().clear(); // Pulisce il grafico da dati precedenti
        PieChart.Data speseData;
        PieChart.Data entrateData;

        float stipendio = utente.getPersonalData().getEntrate();
        float spese_attuali = meseCountability.getSpeseTotaliMensili();

        if (spese_attuali == 0) {
            // Se non ci sono spese, mostra solo il totale delle entrate
            entrateData = new PieChart.Data("Budget", 1);
            pieChart.getData().add(entrateData);
        } else {
            // Mostra il residuo (stipendio - spese) e le uscite totali
            entrateData = new PieChart.Data("entrate", stipendio - spese_attuali);
            speseData = new PieChart.Data("uscite", spese_attuali);
            pieChart.getData().addAll(entrateData, speseData);
        }
        pieChart.setTitle("Budget del mese"); // Imposta il titolo visibile
        counterPieChart = 1;
    }

    /**
     * Gestisce la rotazione dei grafici (Avanti/Indietro).
     * @param actionEvent L'evento generato dal click sui pulsanti freccia.
     */
    public void onChangePiechart(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();

        // Logica per il pulsante "Indietro"
        if (node.equals(btn_backPiechart)) {
            switch (counterPieChart) {
                case 1 -> setPieChartDetailsBills(); // Da 1 torna al 3 (ciclico)
                case 2 -> setPieChartBudgetAmount(); // Da 2 torna all'1
                case 3 -> setPieChartAverageBills(); // Da 3 torna al 2
            }
        }
        // Logica per il pulsante "Avanti"
        else {
            switch (counterPieChart) {
                case 1 -> setPieChartAverageBills(); // Da 1 va al 2
                case 2 -> setPieChartDetailsBills(); // Da 2 va al 3
                case 3 -> setPieChartBudgetAmount(); // Da 3 torna all'1 (ciclico)
            }
        }
    }

    /**
     * Configura il PieChart per mostrare la media delle bollette/spese abitudinarie.
     * Stato: counterPieChart = 2.
     */
    public void setPieChartAverageBills() {
        pieChart.getData().clear();
        chartData = new ArrayList<>();

        // Recupera la lista delle medie (SimpleEntry è una coppia Chiave-Valore)
        List<AbstractMap.SimpleEntry<String, Float>> lista = meseCountability.getAverageBills(5);
        if (lista.isEmpty()){
            pieChart.getData().add(new PieChart.Data("Vuoto", 1));

        }else {
            // Trasforma i dati del modello in dati per il grafico JavaFX
            lista.forEach(token -> chartData.add(new PieChart.Data(token.getKey(), token.getValue())));

            pieChart.getData().addAll(chartData);
        }


        pieChart.setTitle("Spese abitudinarie");
        counterPieChart = 2;
    }

    /**
     * Configura il PieChart per mostrare il dettaglio analitico delle spese.
     * Stato: counterPieChart = 3.
     */
    public void setPieChartDetailsBills() {
        pieChart.getData().clear();

        if (meseCountability.getSpeseTotaliMensili() == 0) {
            // Se non ci sono spese, mostra un grafico vuoto o di default
            pieChart.getData().add(new PieChart.Data("Vuoto", 1));

        } else {
            chartData = new ArrayList<>();
            // Recupera la lista dettagliata delle spese
            List<AbstractMap.SimpleEntry<String, Float>> lista = meseCountability.getDetailsBills();
            lista.forEach(token ->chartData.add(new PieChart.Data(token.getKey(), token.getValue())));
            pieChart.getData().addAll(chartData);
        }
        pieChart.setTitle("Spese nel dettaglio");
        counterPieChart = 3;
    }

    /**
     * Metodo specifico per navigare verso la sezione Note.
     * Carica il file FXML delle note e lo imposta come radice della scena.
     */
    public void goToNotes(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/notes-view.fxml"
            ));
            Parent root = loader.load();

            // Recupera lo stage attuale tramite un nodo della UI (containerInit)
            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento della vista Note", e);
        }
    }

    /**
     * (Deprecato) Metodo per tornare al Login.
     * Si consiglia di usare changePage() per uniformità.
     */
    public void ReturnPage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/login-view.fxml"
            ));
            Parent root = loader.load();
            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo centrale per la navigazione.
     * Determina quale pulsante è stato cliccato e cambia la scena di conseguenza.
     */
    public void changePage(ActionEvent actionEvent) {
        Object sourceBtn = actionEvent.getSource();
        String destinazione = "";

        // Identifica la stringa di destinazione in base al bottone cliccato
        if (sourceBtn == btn_GoLogout) {
            destinazione = "quit";
        } else if (sourceBtn == btn_GoNotes) {
            destinazione = "note";
        } else if (sourceBtn == btn_GoGraph) {
            destinazione = "graph";
        }

        // Recupera il percorso del file FXML tramite il metodo helper pathPage
        String fxmlPath = pathPage(destinazione);

        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                throw new FileNotFoundException("File non trovato: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            // Effettua il cambio effettivo della vista
            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore critico: " + e.getMessage());
        }
    }

    /**
     * Helper metod (Dizionario): associa una parola chiave al percorso fisico del file FXML.
     * @param destinazione Stringa identificativa della pagina.
     * @return Il path completo della risorsa FXML.
     */
    private String pathPage(String destinazione) {
        return switch (destinazione) {
            case "quit" -> "/com/gomiero/progettonegomiero/views/login-views.fxml";
            case "note" -> "/com/gomiero/progettonegomiero/views/notes-views.fxml";
            case "graph" -> "/com/gomiero/progettonegomiero/views/graph-views.fxml";
            default -> "/com/gomiero/progettonegomiero/views/home-view.fxml";
        };
    }
}