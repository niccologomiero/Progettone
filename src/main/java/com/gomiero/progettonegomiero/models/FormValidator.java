package com.gomiero.progettonegomiero.models;

import javafx.scene.control.*;

/**
 * Classe di utilità per la validazione dei dati inseriti nel form.
 * Contiene logiche statiche per controllare la correttezza di ogni singolo step
 * o dell'intero set di dati finanziari.
 */
public class FormValidator {

    /**
     * Esegue una validazione globale di tutti i campi presenti nei vari step.
     * @return un oggetto ValidationResult contenente l'esito e gli eventuali messaggi d'errore.
     */
    public static ValidationResult validateAllSteps(
            Spinner<Integer> eta,
            TextArea lavoro,
            Spinner<Integer> entrate,
            Spinner<Integer> speseTotali,
            Spinner<Integer> goalMoney,
            Spinner<Integer> goalTime,
            Spinner<Integer> risparmi,
            Spinner<Integer> debitiDettaglio) {

        ValidationResult result = new ValidationResult();

        // Validazione aggregata: richiama le logiche specifiche per ogni categoria di dato
        if (!isValidAge(eta.getValue())) {
            result.addError("Età deve essere tra 18 e 100 anni");
        }

        if (!isValidText(lavoro.getText())) {
            result.addError("Inserisci una professione");
        }

        if (!isValidAmount(Double.valueOf(entrate.getValue()))) {
            result.addError("Le entrate devono essere un numero positivo");
        }

        if (!isValidAmount(Double.valueOf(speseTotali.getValue()))) {
            result.addError("Le spese totali devono essere un numero positivo");
        }

        if (goalMoney.getValue() < 0 || goalTime.getValue() < 0) {
            result.addError("Gli obiettivi non possono essere negativi");
        }

        if (risparmi.getValue() < 0) {
            result.addError("I risparmi non possono essere negativi");
        }

        return result;
    }

    /**
     * Valida i dati dello Step 1 (Dati personali e reddito).
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
     * Valida i dati dello Step 2 (Uscite finanziarie).
     */
    public static ValidationResult validateStep2(Spinner<Integer> speseTotali) {
        ValidationResult result = new ValidationResult();

        if (!isValidAmount(Double.valueOf(speseTotali.getValue()))) {
            result.addError("Le spese totali devono essere un numero positivo");
        }

        return result;
    }

    /**
     * Valida i dati dello Step 3 (Obiettivi di risparmio).
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
     * Valida i dati dello Step 4 (Situazione patrimoniale pregressa).
     */
    public static ValidationResult validateStep4(Spinner<Integer> risparmi, Spinner<Integer> debitiAttivi) {
        ValidationResult result = new ValidationResult();

        // Controllo anti-null per evitare crash durante l'unboxing
        if (risparmi.getValue() == null || debitiAttivi.getValue() == null) {
            result.addError("Dato illeggibile");
        }
        else if (risparmi.getValue() < 0 || debitiAttivi.getValue() < 0) {
            result.addError("I dati non possono essere negativi");
        }

        return result;
    }


    // --- Metodi Helper (Privati) ---
    // Questi metodi contengono le regole di business atomiche

    /**
     * Verifica che l'età sia compresa in un range realistico.
     */
    private static boolean isValidAge(Integer age) {
        return age != null && age >= 18 && age <= 100;
    }

    /**
     * Verifica che la stringa non sia vuota e abbia una lunghezza minima (almeno 2 caratteri).
     */
    private static boolean isValidText(String text) {
        return text != null && !text.trim().isEmpty() && text.trim().length() >= 2;
    }

    /**
     * Verifica che l'importo economico sia strettamente superiore a zero.
     */
    private static boolean isValidAmount(Double amount) {
        return amount != null && amount > 0;
    }

    /**
     * Controlla se lo Spinner è stato lasciato vuoto.
     */
    public static boolean isSpinnerEmpty(Spinner<?> spinner) {
        return spinner.getValue() == null;
    }

    /**
     * Classe Inner per incapsulare l'esito della validazione.
     * Permette di accumulare più messaggi di errore in un'unica stringa formattata.
     */
    public static class ValidationResult {
        private StringBuilder errors = new StringBuilder();
        private boolean isValid = true;

        /**
         * Aggiunge un errore alla lista e imposta lo stato a 'non valido'.
         */
        public void addError(String error) {
            if (!isValid) {
                errors.append("\n"); // A capo se ci sono già altri errori
            }
            errors.append("• ").append(error); // Formattazione a elenco puntato
            isValid = false;
        }

        /**
         * @return true se non sono stati riscontrati errori.
         */
        public boolean isValid() {
            return isValid;
        }

        /**
         * @return La stringa completa contenente tutti gli errori accumulati.
         */
        public String getErrors() {
            return errors.toString();
        }

        /**
         * Resetta il risultato per un nuovo ciclo di validazione.
         */
        public void clear() {
            errors = new StringBuilder();
            isValid = true;
        }
    }
}