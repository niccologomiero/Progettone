package com.gomiero.progettonegomiero.controllers;

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
import java.util.Objects;
import java.util.Stack;

public class NotesController {
    @FXML
    private BorderPane contenutoNote;
    @FXML
    private BorderPane containerInit;

    private Utente utente;
    private String tempNickName;


    public void setterUtente(Utente t, String nickName) {
        this.utente = t;
        this.tempNickName = nickName;
        contenutoNote.getChildren().clear();
        //aggiorna UI
        aggiornaInterfaceNotes();
    }

    public void setTempNickName(String tempNickName) {
        this.tempNickName = tempNickName;
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
            if (loader.getController() instanceof TutorialNotesController){
                ((TutorialNotesController) controller).setUtente(utente);
            }else if(controller instanceof PersonalNotesController){
                ((PersonalNotesController) controller).setUtente(utente);
            }
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

            HomeController homeController = loader.getController();
            homeController.setterUtente(utente);
            homeController.setTempNickName(tempNickName);
            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
