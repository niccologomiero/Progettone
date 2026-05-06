package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.DataBase;
import com.gomiero.progettonegomiero.models.GetData;
import com.gomiero.progettonegomiero.models.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NotesController implements GetData {
    @FXML
    private BorderPane contenutoNote;
    @FXML
    private BorderPane containerInit;

    private Utente utente;
    private DataBase dataBase;

    public void initialize(){
        getData();
        contenutoNote.getChildren().clear();
        aggiornaInterfaceNotes();
    }

    @Override
    public void getData(){
        this.dataBase = DataBase.getInstance();
        this.utente = dataBase.getUtenteLogged();
    }


    private void aggiornaInterfaceNotes() {
        if (this.utente.getNotes().isEmpty()){
            loadPersonalNotes("/com/gomiero/progettonegomiero/views/notes-plugin/tutorialNotes-views.fxml");
        }else {
            loadPersonalNotes("/com/gomiero/progettonegomiero/views/notes-plugin/personalNotes-views.fxml");
        }
    }



    private void loadPersonalNotes(String fxmlPath){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node node = loader.load();

            // RECUPERA IL CONTROLLER DELLA SOTTOPAGINA
            Object controller = loader.getController();

            StackPane wrapper = new StackPane(node);
            contenutoNote.setCenter(wrapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ReturnPage(ActionEvent actionEvent) {
        System.out.println("ci sono");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/home-view.fxml"
            ));
            Parent root = loader.load();
            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
