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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller per la vista Home (Dashboard).
 * Gestisce la visualizzazione del portfolio dell'utente e la navigazione verso altre sezioni.
 */
public class HomeController implements GetData {
    // Riferimento all'oggetto utente (dati persistenti)
    private DataBase dataBase;
    // Riferimento all'oggetto utente (dati persistenti)
    private Utente utente;
   //PieChart dati
    private ArrayList<PieChart.Data> pieChartDatas;
    //Classe di supporto alla prelevazione dei dati;
    private MeseCountability meseCountability;
    private HashMap<Integer,ArrayList<PieChart.Data>> chartHashMap = new HashMap<>();
    private int counterPieChart = 0;
    @FXML
    private ArrayList<PieChart.Data> chartA,chartB,chartC;
    // --- Elementi UI iniettati dal file FXML ---
    @FXML
    private Label welcomeUser; // Etichetta del titolo (es. "Portfolio di Mario")
    @FXML
    public Button btn_GoLogout;
    @FXML
    public Button btn_GoGraph;
    @FXML
    public Button btn_GoNotes;
     // Grafico a barre (da implementare)
    @FXML
    private PieChart pieChart;
    @FXML
    private Button btn_backPiechart;
    @FXML
    private Button btn_nextPiechart;
// Asse delle categorie del grafico
    @FXML
    private VBox containerInit;                // Nodo radice della vista per ottenere la finestra (Stage)


    /**
     * Inizializza l'utente corrente nel controller.
     */
    @Override
    public void getData() {
        this.dataBase = DataBase.getInstance();
        this.utente = dataBase.getUtenteLogged();
        if (utente != null){
            welcomeUser.setText(utente.getUsername() +"'Home");
            meseCountability = utente.getContabilities();
            //TODO dare benvenuto all'utente
        }else{
            //TODO gestire
            return;
        }
    }

    /**
     * Metodo chiamato automaticamente al caricamento del file FXML.
     */
    @FXML
    private void initialize(){
        chartHashMap.put(1,chartA);
        chartHashMap.put(2,chartB);
        chartHashMap.put(3,chartC);

       getData();
       BudgetPieChart();
    }
    //Grafico iniziale dove si vedrà il budget rimanente
    public void BudgetPieChart(){
        PieChart.Data speseData;
        PieChart.Data entrateData;
        float stipendio = utente.getPersonalData().getEntrate();
        float spese_attuali = meseCountability.getSpeseTotaliMensili();
        if (spese_attuali == 0){
            entrateData = new PieChart.Data("entrate",stipendio);
            pieChart.getData().add(entrateData);
        }
        else {
            entrateData = new PieChart.Data("entrate",stipendio - spese_attuali);
            speseData = new PieChart.Data("uscite", stipendio);
            pieChart.getData().add(entrateData);
            pieChart.getData().add(speseData);
        }
        pieChart.setId("Bugdet del mese");
        counterPieChart = 1;
    }
    public void onChangePiechart(ActionEvent actionEvent){
        Node node = (Node) actionEvent.getSource();
        if (node.equals(btn_backPiechart)){
            switch (counterPieChart){
                case 1:
                    //non faccio nulla oppure farlo ripartire dalla fine
                    break;
                case 2:
                    //porto il piechart al budget
                    BudgetPieChart();
                    break;
                case 3:
                    //porto il piechart alle spese solite

                    break;
                default:
                    break;
            }
        }else {
            switch (counterPieChart){
                case 1:
                    //porto il piechart alle spese solite
                    break;
                case 2:
                    //porto il piechart alle spese dettagliate
                    BudgetPieChart();
                    break;
                case 3:
                    //non faccio nulla oppure farlo ripartire dall'inizio

                    break;
                default:
                    break;
            }
        }
    }
    /**
     * Imposta la modalità di visualizzazione del grafico a torta sulla "Media delle bollette".
     * Il valore 2 del contatore indica al sistema di calcolare e mostrare i valori medi.
     */
    public void setPieChartAverageBills() {
        // Imposta il selettore di stato per la visualizzazione delle spese solite
        //TODO da continuare
        pieChart.setId("Spese abitudinarie");
        counterPieChart = 2;
    }

    /**
     * Imposta la modalità di visualizzazione del grafico a torta sui "Dettagli delle bollette".
     * Il valore 3 del contatore indica al sistema di mostrare la scomposizione analitica dei costi.
     */
    public void setPieChartDetailsBills() {
        // Imposta il selettore di stato per la visualizzazione dettagliata
        //TODO da implementare
        pieChart.setId("Spese nel dettaglio");
        counterPieChart = 3;
    }
    /**
     * Naviga verso la vista delle Note.
     * Passa l'oggetto utente al NotesController per mantenere la sessione.
     */
    public void goToNotes(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/notes-view.fxml"
            ));
            Parent root = loader.load();

//            Recupera il controller della nuova pagina e inietta i dati
//            NotesController notesController = loader.getController();

            // Cambia la radice della scena corrente (root)
            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * (Deprecato) Torna alla pagina di Login.
     * Sostituito dalla logica dinamica del metodo changePage.
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
     * Metodo unificato per gestire il cambio di pagina.
     * Identifica il pulsante cliccato e carica il file FXML corrispondente.
     */
    public void changePage(ActionEvent actionEvent){
        Object sourceBtn = actionEvent.getSource();
        String destinazione = "";

        // Identifica la destinazione in base al pulsante premuto
        if(sourceBtn == btn_GoLogout){
            destinazione = "quit";
        } else if (sourceBtn == btn_GoNotes){
            destinazione = "note";
        } else if (sourceBtn == btn_GoGraph) {
            destinazione = "graph";
        }

        // Ottiene il percorso del file FXML
        String fxmlPath = pathPage(destinazione);

        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null){
                throw new FileNotFoundException("Impossibile trovare il file FXML: " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            // Ottiene lo Stage corrente
            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Errore critico durante il cambio pagina: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mappa una stringa di destinazione al percorso fisico del file FXML.
     * @param destinazione stringa identificativa.
     * @return il percorso della risorsa FXML.
     */
    private String pathPage(String destinazione){
        return switch (destinazione) {
            case "quit" -> "/com/gomiero/progettonegomiero/views/login-views.fxml";
            case "note" -> "/com/gomiero/progettonegomiero/views/notes-views.fxml";
            case "graph" -> "/com/gomiero/progettonegomiero/views/graph-views.fxml";
            default -> "/com/gomiero/progettonegomiero/views/home-view.fxml";
        };
    }
}