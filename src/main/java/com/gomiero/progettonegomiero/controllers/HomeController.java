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
    private List<PieChart> piecharts = new ArrayList<>(); // Nota: di solito non è @FXML se non è un elemento grafico
    private int currentPieIndex = 0;
    @FXML
    private Label welcomeUser;        // Messaggio di benvenuto (es: "Mario's Home")
    @FXML
    public Button btn_GoLogout, btn_GoGraph, btn_GoNotes; // Pulsanti di navigazione
    @FXML
    private PieChart pieChart; // Il componente grafico a torta
    @FXML
    private PieChart pieChartBudget;
    @FXML
    private PieChart pieChartAverageBills;
    @FXML
    private PieChart pieChartDetailsBills;
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
        getData(); // Carica i dati utente

        // Popoliamo la lista dei grafici
        piecharts.add(pieChartBudget);
        piecharts.add(pieChartAverageBills);
        piecharts.add(pieChartDetailsBills);

        // Nasconde la legenda sotto il grafico
        pieChartBudget.setLegendVisible(false);
        pieChartAverageBills.setLegendVisible(false);
        pieChartDetailsBills.setLegendVisible(false);

        // --- RIMOZIONE DELLE PAROLE/ETICHETTE SUL GRAFICO ---
        pieChartBudget.setLabelsVisible(false);
        pieChartAverageBills.setLabelsVisible(false);
        pieChartDetailsBills.setLabelsVisible(false);
        // -----------------------------------------------------

        // Configura e popola i dati
        setPieChartBudgetAmount();
        setPieChartAverageBills();
        setPieChartDetailsBills();

        // Stato iniziale della visibilità
        updatePieChartsVisibility();
    }

    /**
     * Configura il PieChart del Budget (Entrate vs Uscite).
     */
    public void setPieChartBudgetAmount() {
        pieChartBudget.getData().clear();
        pieChartBudget.setTitle("Budget del mese");

        if (utente == null || meseCountability == null) return;

        float stipendio = utente.getPersonalData().getEntrate();
        float spese_attuali = meseCountability.getSpeseTotaliMensili();

        if (spese_attuali == 0) {
            pieChartBudget.getData().add(new PieChart.Data("Budget Disponibile (Intero)", stipendio > 0 ? stipendio : 1));

        } else {
            float residuo = stipendio - spese_attuali;
            // Se le spese superano lo stipendio, evitiamo fette negative nel grafico
            pieChartBudget.getData().add(new PieChart.Data("Entrate Residue", Math.max(0, residuo)));
            pieChartBudget.getData().add(new PieChart.Data("Uscite", spese_attuali));
        }
    }
    /**
     * Configura il PieChart per mostrare la media delle bollette/spese abitudinarie.
     */
    public void setPieChartAverageBills() {
        pieChartAverageBills.getData().clear();
        pieChartAverageBills.setTitle("Spese abitudinarie");

        if (meseCountability == null) return;

        List<AbstractMap.SimpleEntry<String, Float>> lista = meseCountability.getAverageBills(5);
        if (lista == null || lista.isEmpty()) {
            pieChartAverageBills.getData().add(new PieChart.Data("", 1));
            pieChartAverageBills.setStyle(".default-color0.chart-pie { -fx-pie-color: #d3d3d3; }");
        } else {
            ArrayList<PieChart.Data> chartData = new ArrayList<>();
            lista.forEach(token -> chartData.add(new PieChart.Data(token.getKey(), token.getValue())));
            pieChartAverageBills.getData().addAll(chartData);
        }
    }

    /**
     * Configura il PieChart per mostrare il dettaglio analitico delle spese.
     */
    public void setPieChartDetailsBills() {
        pieChartDetailsBills.getData().clear();
        pieChartDetailsBills.setTitle("Spese nel dettaglio");

        if (meseCountability == null || meseCountability.getSpeseTotaliMensili() == 0) {
            pieChartDetailsBills.getData().add(new PieChart.Data("Nessuna spesa", 1));
        } else {
            List<AbstractMap.SimpleEntry<String, Float>> lista = meseCountability.getDetailsBills();
            if (lista != null) {
                ArrayList<PieChart.Data> chartData = new ArrayList<>();
                lista.forEach(token -> chartData.add(new PieChart.Data(token.getKey(), token.getValue())));
                pieChartDetailsBills.getData().addAll(chartData);
            }
        }
    }

    @FXML
    public void onChangePiechart(ActionEvent actionEvent) {
        Node sourceNode = (Node) actionEvent.getSource();

        if (sourceNode.equals(btn_backPiechart)) {
            // Sposta indietro (se sotto zero, ricomincia dalla fine)
            currentPieIndex--;
            if (currentPieIndex < 0) {
                currentPieIndex = piecharts.size() - 1;
            }
        } else if (sourceNode.equals(btn_nextPiechart)) {
            // Sposta avanti (se supera la dimensione, ricomincia da zero)
            currentPieIndex++;
            if (currentPieIndex >= piecharts.size()) {
                currentPieIndex = 0;
            }
        }

        // Applica i cambiamenti di visibilità sulla base del nuovo indice corrente
        updatePieChartsVisibility();
    }

    /**
     * Helper Method: Imposta visibile solo il grafico corrispondente a `currentPieIndex`.
     */
    private void updatePieChartsVisibility() {
        for (int i = 0; i < piecharts.size(); i++) {
            piecharts.get(i).setVisible(i == currentPieIndex);
        }
    }



    /**
     * Metodo specifico per navigare verso la sezione Note.
     * Carica il file FXML delle note e lo imposta come radice della scena.
     */
    @FXML
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
    @FXML
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