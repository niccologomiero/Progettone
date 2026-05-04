package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.FormValidator;
import com.gomiero.progettonegomiero.models.Utente;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class FormController implements SetterUtente {

    private Utente utente;
    private ArrayList<VBox> containerVbox;
    private int currentStep = 0;

    @FXML
    private BorderPane containerInit;
    @FXML
    public Button btn_GoLogout;
    @FXML
    public Button btn_SaveData;
    @FXML
    public Label showErrors;
    @FXML
    public Button btn_before_step;
    @FXML
    public Button btn_next_Step;
    @FXML
    public VBox step1;
    @FXML
    public VBox step2;
    @FXML
    public VBox step3;
    @FXML
    public VBox step4;
    @FXML
    public VBox step5;

    // --- Step 1 ---
    @FXML
    private Spinner<Integer> eta;
    @FXML
    private TextArea lavoro;
    @FXML
    private Spinner<Integer> entrate;

    // --- Step 2 ---
    @FXML
    private Spinner<Integer> speseTotali;

    // --- Step 3 ---
    @FXML
    private Spinner<Integer> goalMoney;
    @FXML
    private Spinner<Integer> goalTime;
    @FXML
    private CheckBox isfondoEmergenza;
    @FXML
    private TextArea fondoEmergenza;
    @FXML
    private CheckBox isfondoAuto_casa;
    @FXML
    private TextArea fondoAuto_casa;
    @FXML
    private CheckBox isFondoViaggi;
    @FXML
    private TextArea fondoViaggi;
    @FXML
    private CheckBox isfondoInvestire;
    @FXML
    private TextArea fondoInvestire;

    // --- Step 4 ---
    @FXML
    private Spinner<Integer> risparmi;
    @FXML
    private TextArea debitiDettaglio;

    public void setterUtente(Utente t) {
        this.utente = t;
    }

    @FXML
    public void initialize(){
        Platform.runLater(() -> {
            containerVbox = new ArrayList<>();
            containerVbox.add(step1);
            containerVbox.add(step2);
            containerVbox.add(step3);
            containerVbox.add(step4);
            containerVbox.add(step5);

            // Mostra solo il primo step
            for (int i = 1; i < containerVbox.size(); i++) {
                containerVbox.get(i).setVisible(false);
            }
            updateButtonStates();
            clearErrors();
        });
    }

    public void ReturnPage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/login-view.fxml"
            ));
            Parent root = loader.load();
            LoginController controller = loader.getController();

            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onChangeStep(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();

        if (source.getId().equals(btn_before_step.getId()) && currentStep > 0) {
            // Torna indietro senza validazione
            showStep(currentStep - 1);
        } else if (source.getId().equals(btn_next_Step.getId()) && currentStep < containerVbox.size() - 1) {
            // Valida lo step attuale prima di procedere
            FormValidator.ValidationResult validationResult = validateCurrentStep(currentStep);

            if (validationResult.isValid()) {
                // Se valido, vai al prossimo step
                showStep(currentStep + 1);
            } else {
                // Se non valido, mostra gli errori
                showError(validationResult.getErrors());
            }
        }
    }

    private int findCurrentStep() {
        for (int i = 0; i < containerVbox.size(); i++) {
            if (containerVbox.get(i).isVisible()) {
                return i;
            }
        }
        return 0;
    }

    private void showStep(int stepIndex) {
        // Nascondi tutti
        for (VBox step : containerVbox) {
            step.setVisible(false);
        }

        // Mostra solo lo step corrente
        containerVbox.get(stepIndex).setVisible(true);
        currentStep = stepIndex;

        // Aggiorna i bottoni
        updateButtonStates();

        // Pulisci gli errori quando cambi step
        clearErrors();
    }

    private void updateButtonStates() {
        btn_before_step.setDisable(currentStep == 0);
        btn_next_Step.setDisable(currentStep == containerVbox.size() - 1);
    }

    private FormValidator.ValidationResult validateCurrentStep(int stepIndex) {
        switch (stepIndex) {
            case 0: // Step 1
                return FormValidator.validateStep1(eta, lavoro, entrate);
            case 1: // Step 2
                return FormValidator.validateStep2(speseTotali);
            case 2: // Step 3
                return FormValidator.validateStep3(goalMoney, goalTime);
            case 3: // Step 4
                return FormValidator.validateStep4(risparmi);
            case 4: // Step 5
                return FormValidator.validateStep5();
            default:
                return new FormValidator.ValidationResult();
        }
    }

    /**
     * Mostra i messaggi di errore
     */
    private void showError(String error) {
        showErrors.setText(error);
        showErrors.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");
    }

    /**
     * Pulisce i messaggi di errore
     */
    private void clearErrors() {
        showErrors.setText("");
        showErrors.setStyle("");
    }

    /**
     * Salva i dati del form
     */
    public void saveFormData() {
        FormValidator.ValidationResult validation = FormValidator.validateAllSteps(
                eta, lavoro, entrate,
                speseTotali,
                goalMoney, goalTime,
                risparmi, debitiDettaglio
        );

        if (validation.isValid()) {
            // TODO: Salva i dati nel database
            showSuccess("Dati salvati con successo!");
        } else {
            showError(validation.getErrors());
        }
    }

    private void showSuccess(String message) {
        showErrors.setText(message);
        showErrors.setStyle("-fx-text-fill: #00aa00; -fx-font-weight: bold;");
    }
}