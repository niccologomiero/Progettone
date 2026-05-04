package com.gomiero.progettonegomiero.models;

import javafx.scene.control.*;

public class FormValidator {

    /**
     * Valida tutti gli input del form
     * @return true se tutti gli input sono validi, false altrimenti
     */
    public static ValidationResult validateAllSteps(
            Spinner<Integer> eta,
            TextArea lavoro,
            Spinner<Integer> entrate,
            Spinner<Integer> speseTotali,
            Spinner<Integer> goalMoney,
            Spinner<Integer> goalTime,
            Spinner<Integer> risparmi,
            TextArea debitiDettaglio) {

        ValidationResult result = new ValidationResult();

        // Step 1 validation
        if (!isValidAge(eta.getValue())) {
            result.addError("Età deve essere tra 18 e 100 anni");
        }

        if (!isValidText(lavoro.getText())) {
            result.addError("Inserisci una professione");
        }

        if (!isValidAmount(Double.valueOf(entrate.getValue()))) {
            result.addError("Le entrate devono essere un numero positivo");
        }

        // Step 2 validation
        if (!isValidAmount(Double.valueOf(speseTotali.getValue()))) {
            result.addError("Le spese totali devono essere un numero positivo");
        }

        // Step 3 validation
        if (goalMoney.getValue() < 0 || goalTime.getValue() < 0) {
            result.addError("Gli obiettivi non possono essere negativi");
        }

        // Step 4 validation
        if (risparmi.getValue() < 0) {
            result.addError("I risparmi non possono essere negativi");
        }

        return result;
    }

    /**
     * Valida il Step 1
     */
    public static ValidationResult validateStep1(
            Spinner<Integer> eta,
            TextArea lavoro,
            Spinner<Integer> entrate) {

        ValidationResult result = new ValidationResult();

        if (!isValidAge(eta.getValue())) {
            result.addError("Età deve essere tra 18 e 100 anni");
        }

        if (!isValidText(lavoro.getText())) {
            result.addError("Inserisci una professione valida");
        }

        if (!isValidAmount(Double.valueOf(entrate.getValue()))) {
            result.addError("Le entrate devono essere un numero positivo");
        }

        return result;
    }

    /**
     * Valida il Step 2
     */
    public static ValidationResult validateStep2(Spinner<Integer> speseTotali) {
        ValidationResult result = new ValidationResult();

        if (!isValidAmount(Double.valueOf(speseTotali.getValue()))) {
            result.addError("Le spese totali devono essere un numero positivo");
        }

        return result;
    }

    /**
     * Valida il Step 3
     */
    public static ValidationResult validateStep3(
            Spinner<Integer> goalMoney,
            Spinner<Integer> goalTime) {

        ValidationResult result = new ValidationResult();

        if (goalMoney.getValue() < 0) {
            result.addError("Il target di risparmio non può essere negativo");
        }

        if (goalTime.getValue() < 1) {
            result.addError("L'orizzonte temporale deve essere almeno 1 anno");
        }

        return result;
    }

    /**
     * Valida il Step 4
     */
    public static ValidationResult validateStep4(Spinner<Integer> risparmi) {
        ValidationResult result = new ValidationResult();

        if (risparmi.getValue() < 0) {
            result.addError("I risparmi non possono essere negativi");
        }

        return result;
    }

    /**
     * Valida il Step 5 - Niente da validare obbligatoriamente
     */
    public static ValidationResult validateStep5() {
        return new ValidationResult();
    }

    // --- Helper Methods ---

    /**
     * Controlla se l'età è valida (18-100)
     */
    private static boolean isValidAge(Integer age) {
        return age != null && age >= 18 && age <= 100;
    }

    /**
     * Controlla se il testo non è vuoto e ha almeno 2 caratteri
     */
    private static boolean isValidText(String text) {
        return text != null && !text.trim().isEmpty() && text.trim().length() >= 2;
    }

    /**
     * Controlla se l'importo è positivo
     */
    private static boolean isValidAmount(Double amount) {
        return amount != null && amount > 0;
    }

    /**
     * Controlla se uno Spinner ha un valore
     */
    public static boolean isSpinnerEmpty(Spinner<?> spinner) {
        return spinner.getValue() == null;
    }

    /**
     * Classe per gestire i risultati della validazione
     */
    public static class ValidationResult {
        private StringBuilder errors = new StringBuilder();
        private boolean isValid = true;

        public void addError(String error) {
            if (!isValid) {
                errors.append("\n");
            }
            errors.append("• ").append(error);
            isValid = false;
        }

        public boolean isValid() {
            return isValid;
        }

        public String getErrors() {
            return errors.toString();
        }

        public void clear() {
            errors = new StringBuilder();
            isValid = true;
        }
    }
}
