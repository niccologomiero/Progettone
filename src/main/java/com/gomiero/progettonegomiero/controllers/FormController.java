package com.gomiero.progettonegomiero.controllers;

import com.gomiero.progettonegomiero.models.FormData;
import com.gomiero.progettonegomiero.models.FormValidator;
import com.gomiero.progettonegomiero.models.Utente;
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

/**
 * Controller per la gestione del form multi-step.
 * Gestisce l'inserimento dei dati utente, la validazione per ogni step e il salvataggio finale.
 */
public class FormController implements SetterUtente {

    // Riferimento all'utente corrente
    private Utente utente;
    // Lista che contiene i contenitori verticali (step) del form
    private ArrayList<VBox> containerVbox;
    // Indice dello step attualmente visualizzato
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

    // Riferimenti ai vari step definiti nel file FXML
    @FXML
    public VBox step1, step2, step3, step4, step5;

    // --- Elementi Step 1: Dati Personali ---
    @FXML
    private Spinner<Integer> eta;
    @FXML
    private TextArea lavoro;
    @FXML
    private Spinner<Integer> entrate;

    // --- Elementi Step 2: Spese ---
    @FXML
    private Spinner<Integer> speseTotali;

    // --- Elementi Step 3: Obiettivi ---
    @FXML
    private Spinner<Integer> goalMoney;
    @FXML
    private Spinner<Integer> goalTime;
    @FXML
    private CheckBox isfondoEmergenza, isfondoAuto_casa, isFondoViaggi, isfondoInvestire;

    // --- Elementi Step 4: Risparmi e Debiti ---
    @FXML
    private Spinner<Integer> risparmi;
    @FXML
    private Spinner<Integer> debitiDettaglio;

    /**
     * Implementazione dell'interfaccia SetterUtente per ricevere l'oggetto Utente
     * da altri controller (es. dal Login).
     */
    @Override
    public void setterUtente(Utente t) {
        this.utente = t;
    }

    /**
     * Metodo di inizializzazione di JavaFX. Configura la struttura a step.
     */
    @FXML
    public void initialize(){
        // Raggruppa gli step in una lista per gestirli ciclicamente
        containerVbox = new ArrayList<>();
        containerVbox.add(step1);
        containerVbox.add(step2);
        containerVbox.add(step3);
        containerVbox.add(step4);
        containerVbox.add(step5);

        // Nasconde tutti gli step tranne il primo (indice 0)
        for (int i = 1; i < containerVbox.size(); i++) {
            containerVbox.get(i).setVisible(false);
        }

        updateButtonStates(); // Configura i tasti Avanti/Indietro
        clearErrors();        // Pulisce eventuali messaggi residui
    }

    /**
     * Ritorna alla pagina di Login.
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
     * Naviga verso la Home Page dopo aver completato il form.
     */
    public void toHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/gomiero/progettonegomiero/views/home-view.fxml"
            ));
            Parent root = loader.load();
            HomeController controller = loader.getController();

            // Passa l'utente aggiornato al controller della Home
            if (controller != null) ((SetterUtente) controller).setterUtente(utente);

            Stage stage = (Stage) containerInit.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handler per il pulsante di salvataggio finale.
     */
    public void onSave(ActionEvent actionEvent){
        saveFormData();
    }

    /**
     * Gestisce la navigazione tra gli step (Avanti e Indietro).
     * Include la logica di validazione prima di procedere allo step successivo.
     */
    public void onChangeStep(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();

        // Logica per tornare indietro
        if (source.getId().equals(btn_before_step.getId()) && currentStep > 0) {
            showStep(currentStep - 1);
        }
        // Logica per andare avanti
        else if (source.getId().equals(btn_next_Step.getId()) && currentStep < containerVbox.size() - 1 ) {
            // Valida i dati dello step corrente prima di permettere il passaggio al successivo
            FormValidator.ValidationResult validationResult = validateCurrentStep(currentStep);

            if (validationResult.isValid()) {
                showStep(currentStep + 1);
            } else {
                // Se i dati sono errati, mostra l'errore e blocca l'utente
                showError(validationResult.getErrors());
            }
        }
    }

    /**
     * Gestisce la visibilità delle VBox degli step.
     * @param stepIndex L'indice dello step da mostrare.
     */
    private void showStep(int stepIndex) {
        // Nasconde tutti gli step
        for (VBox step : containerVbox) {
            step.setVisible(false);
        }

        // Mostra lo step richiesto e aggiorna l'indice corrente
        containerVbox.get(stepIndex).setVisible(true);
        currentStep = stepIndex;

        // Se siamo all'ultimo step, mostra il tasto per salvare i dati
        if (stepIndex == containerVbox.size() - 1){
            btn_SaveData.setVisible(true);
        }

        updateButtonStates();
        clearErrors();
    }

    /**
     * Attiva o disattiva i pulsanti di navigazione in base alla posizione attuale.
     */
    private void updateButtonStates() {
        btn_before_step.setDisable(currentStep == 0); // Disabilita "Indietro" al primo step
        btn_next_Step.setDisable(currentStep == containerVbox.size() - 1); // Disabilita "Avanti" all'ultimo
    }

    /**
     * Chiama il validatore specifico per lo step attuale.
     */
    private FormValidator.ValidationResult validateCurrentStep(int stepIndex) {
        switch (stepIndex) {
            case 0: return new FormValidator.ValidationResult(); // Step di benvenuto
            case 1: return FormValidator.validateStep1(eta, lavoro, entrate);
            case 2: return FormValidator.validateStep2(speseTotali);
            case 3: return FormValidator.validateStep3(goalMoney, goalTime);
            case 4: return FormValidator.validateStep4(risparmi, debitiDettaglio);
            default: return new FormValidator.ValidationResult();
        }
    }

    /**
     * Formatta e mostra un messaggio di errore in rosso.
     */
    private void showError(String error) {
        showErrors.setText(error);
        showErrors.setStyle("-fx-text-fill: #cc0000; -fx-font-weight: bold;");
    }

    /**
     * Rimuove i messaggi di errore dalla UI.
     */
    private void clearErrors() {
        showErrors.setText("");
        showErrors.setStyle("");
    }

    /**
     * Raccoglie tutti i dati dai campi UI, crea un oggetto FormData
     * e lo assegna all'utente corrente.
     */
    public void saveFormData() {
        // Validazione finale di tutti i campi
        FormValidator.ValidationResult validation = FormValidator.validateAllSteps(
                eta, lavoro, entrate, speseTotali, goalMoney, goalTime, risparmi, debitiDettaglio
        );

        if (validation.isValid()){
            // Trasferimento dati dalla UI al modello FormData
            FormData formData = new FormData(
                    eta.getValue(),
                    lavoro.getText().trim(),
                    entrate.getValue().floatValue(),
                    speseTotali.getValue().floatValue(),
                    goalMoney.getValue().floatValue(),
                    goalTime.getValue().floatValue(),
                    isfondoEmergenza.isSelected(),
                    isfondoAuto_casa.isSelected(),
                    isFondoViaggi.isSelected(),
                    isfondoInvestire.isSelected(),
                    risparmi.getValue().floatValue(),
                    debitiDettaglio.getValue().floatValue());

            // Collega i dati all'utente e cambia scena
            utente.setFormData(formData);
            toHomePage();
        } else {
            showErrors.setText(validation.getErrors());
        }
    }

    /**
     * Feedback visivo per operazioni andate a buon fine.
     */
    private void showSuccess(String message) {
        showErrors.setText(message);
        showErrors.setStyle("-fx-text-fill: #00aa00; -fx-font-weight: bold;");
    }
}