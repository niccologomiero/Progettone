package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public class HomeController {
    private Utente utente;
    private String tempNickName;

    @FXML
    private Label titlePortfolio;
    @FXML
    private BarChart<String,Integer> barChart;
    @FXML
    private CategoryAxis asseX;
    @FXML
    private VBox containerInit;
    private ObservableList<String> mesiNomi = FXCollections.observableArrayList();

    public void setterUtente(Utente t, String nickName) {
        this.utente = t;
        this.tempNickName = nickName;
        if (titlePortfolio != null){
            titlePortfolio.setText("Portfolio di " + nickName);
        }
    }

    @FXML
    private void initialize(){
        if (asseX == null || barChart == null) {
            return; // la view non è più attiva, evita il crash
        }

        if (tempNickName != null){
            titlePortfolio.setStyle("-fx-text-fill: black");
            titlePortfolio.setText("Portfolio di " + tempNickName);
        }

        String[] mesi = DateFormatSymbols.getInstance(Locale.ITALY).getMonths();
        mesiNomi.addAll(Arrays.copyOfRange(mesi,0,12));

        asseX.setCategories(mesiNomi);

        XYChart.Series<String,Integer> serie = new XYChart.Series<>();
        serie.setName("Mesi dell'anno");

        for (String m : mesiNomi){
            serie.getData().add(new XYChart.Data<>(m,(int)(Math.random() * 2000)));
        }
        barChart.getData().add(serie);
    }
        //TODO:esportare controller e l'utente come in notes
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
    //TODO: fai btn per graph page;
}
