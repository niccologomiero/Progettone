package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.Application;
import com.gomiero.progettonegomiero.models.DataBase;
import com.gomiero.progettonegomiero.models.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Controller per la vista Home (Dashboard).
 * Gestisce la visualizzazione del portfolio dell'utente e la navigazione verso altre sezioni.
 */
public class HomeController implements GetData {
    // Riferimento all'oggetto utente (dati persistenti)
    private DataBase dataBase;
    // Riferimento all'oggetto utente (dati persistenti)
    private Utente utente;
    // Variabile temporanea per il nickname (se non ancora salvato nell'oggetto utente)
    private String username;

    // --- Elementi UI iniettati dal file FXML ---
    @FXML
    private Label titlePortfolio; // Etichetta del titolo (es. "Portfolio di Mario")
    @FXML
    public Button btn_GoLogout;
    @FXML
    public Button btn_GoGraph;
    @FXML
    public Button btn_GoNotes;
    @FXML
    private BarChart<String,Integer> barChart; // Grafico a barre (da implementare)
    @FXML
    private CategoryAxis asseX;                // Asse delle categorie del grafico
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
            //TODO dare benvenuto all'utente
        }
    }

    /**
     * Imposta il nickname e aggiorna l'interfaccia grafica.
     * @param nickName il nome da visualizzare nel titolo.
     */
    public void setUsername(String nickName){
        this.username = utente.getUsername();
        if (titlePortfolio != null){
            titlePortfolio.setText("Portfolio di " + nickName);
        }
    }

    /**
     * Metodo chiamato automaticamente al caricamento del file FXML.
     */
    @FXML
    private void initialize(){
       getData();
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