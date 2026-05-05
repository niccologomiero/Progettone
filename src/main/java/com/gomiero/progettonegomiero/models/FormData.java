package com.gomiero.progettonegomiero.models;

/**
 * Classe Modello che rappresenta i dati finanziari e personali raccolti tramite il form.
 * Viene utilizzata per trasportare le informazioni tra i vari controller e memorizzarle nell'utente.
 */
public class FormData {

    // --- Campi Anagrafici e Professionali ---
    private int eta;
    private String lavoro;

    // --- Campi Economici Mensili ---
    private float entrate;
    private float speseTotali;

    // --- Obiettivi Finanziari ---
    private float goalMoney; // Cifra che si desidera raggiungere
    private float goalTime;  // Arco temporale previsto (es. mesi o anni)

    // --- Opzioni e Preferenze (Flags) ---
    private boolean isFondoEmergenza;
    private boolean isFondoMutuo;
    private boolean isFondoViaggi;
    private boolean fondoInvestire;

    // --- Stato Patrimoniale Pregresso ---
    private float risparmiPregressi;       // Soldi già messi da parte
    private float debitiPregressiTotali;   // Totale dei debiti esistenti

    /**
     * Costruttore completo per inizializzare tutti i parametri del form in un'unica soluzione.
     */
    public FormData(int eta, String lavoro, float entrate, float speseTotali, float goalMoney, float goalTime, boolean isFondoEmergenza, boolean isFondoMutuo, boolean isFondoViaggi, boolean fondoInvestire, float risparmiPregressi, float debitiPregressiTotali) {
        this.eta = eta;
        this.lavoro = lavoro;
        this.entrate = entrate;
        this.speseTotali = speseTotali;
        this.goalMoney = goalMoney;
        this.goalTime = goalTime;
        this.isFondoEmergenza = isFondoEmergenza;
        this.isFondoMutuo = isFondoMutuo;
        this.isFondoViaggi = isFondoViaggi;
        this.fondoInvestire = fondoInvestire;
        this.risparmiPregressi = risparmiPregressi;
        this.debitiPregressiTotali = debitiPregressiTotali;
    }

    // --- Metodi Getter e Setter ---
    // Consentono l'accesso controllato e la modifica delle variabili private (Incapsulamento)

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public String getLavoro() {
        return lavoro;
    }

    public void setLavoro(String lavoro) {
        this.lavoro = lavoro;
    }

    public float getEntrate() {
        return entrate;
    }

    public void setEntrate(float entrate) {
        this.entrate = entrate;
    }

    public float getSpeseTotali() {
        return speseTotali;
    }

    public void setSpeseTotali(float speseTotali) {
        this.speseTotali = speseTotali;
    }

    public float getGoalMoney() {
        return goalMoney;
    }

    public void setGoalMoney(float goalMoney) {
        this.goalMoney = goalMoney;
    }

    public float getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(float goalTime) {
        this.goalTime = goalTime;
    }

    public boolean isFondoEmergenza() {
        return isFondoEmergenza;
    }

    public void setFondoEmergenza(boolean fondoEmergenza) {
        isFondoEmergenza = fondoEmergenza;
    }

    public boolean isFondoMutuo() {
        return isFondoMutuo;
    }

    public void setFondoMutuo(boolean fondoMutuo) {
        isFondoMutuo = fondoMutuo;
    }

    public boolean isFondoViaggi() {
        return isFondoViaggi;
    }

    public void setFondoViaggi(boolean fondoViaggi) {
        isFondoViaggi = fondoViaggi;
    }

    public boolean isFondoInvestire() {
        return fondoInvestire;
    }

    public void setFondoInvestire(boolean fondoInvestire) {
        this.fondoInvestire = fondoInvestire;
    }

    public float getRisparmiPregressi() {
        return risparmiPregressi;
    }

    public void setRisparmiPregressi(float risparmiPregressi) {
        this.risparmiPregressi = risparmiPregressi;
    }

    public float getDebitiPregressiTotali() {
        return debitiPregressiTotali;
    }

    public void setDebitiPregressiTotali(float debitiPregressiTotali) {
        this.debitiPregressiTotali = debitiPregressiTotali;
    }
}
