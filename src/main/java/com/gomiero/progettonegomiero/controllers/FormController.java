package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;


public class FormController implements SetterUtente {
    private Utente utente;
    private String tempNickName;

    @FXML
    public Button btn_GoLogout;
    @FXML
    public Button btn_GoGraph;
    @FXML
    public Button btn_GoNotes;

    @FXML
    private VBox containerInit;
    private final ObservableList<String> mesiNomi = FXCollections.observableArrayList();

    public void setterUtente(Utente t) {
        this.utente = t;

    }

    @FXML
    private void initialize(){
    }
    //FIXME:esportare controller e l'utente come in notes
    public void goToNotes(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/notes-view.fxml"
            ));
            Parent root = loader.load();

            NotesController notesController = loader.getController();
            notesController.setterUtente(utente,tempNickName);

            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //deprecated: questi metodi avranno un unico
    // metodo che riconosce che tipo di btn è cliccato
    public void ReturnPage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/login-view.fxml"
            ));
            Parent root = loader.load();
            Controller controller = loader.getController();

            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void changePage(ActionEvent actionEvent){
        Object sourceBtn = actionEvent.getSource();
        String destinazione = "";
        if(sourceBtn == btn_GoLogout){
            destinazione = "quit";
        }else if (sourceBtn == btn_GoNotes){
            destinazione = "note";
        } else if (sourceBtn == btn_GoGraph) {
            destinazione = "graph";
        }

        String fxmlPath = pathPage(destinazione);
        try{
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null){
                throw new FileNotFoundException("Impossibile trovare il file FXML: " + fxmlPath);
            }
            FXMLLoader loader = new FXMLLoader(resource);

            Parent root = loader.load();

            Controller controller = loader.getController();

            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

            if (controller instanceof SetterUtente) ((SetterUtente) controller).setterUtente(utente);

        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            System.err.println("Errore critico durante il cambio pagina" + e.getMessage());
            e.printStackTrace();
        }

    }
    private String pathPage(String destinazione){
        String path = switch (destinazione) {
            case "quit" -> "/com/gomiero/progettonegomiero/views/login-views.fxml";
            case "note" -> "/com/gomiero/progettonegomiero/views/notes-views.fxml";
            case "graph" -> "/com/gomiero/progettonegomiero/views/graph-views.fxml";
            default -> "/com/gomiero/progettonegomiero/views/home-view.fxml";
        };
        return path;
    }
}
