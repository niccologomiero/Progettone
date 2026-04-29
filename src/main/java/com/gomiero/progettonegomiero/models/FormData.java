package com.gomiero.progettonegomiero.models;

public class FormData {
    private int eta;
    private String lavoro;
    private float entrate;
    private float speseTotali;
    private float goalMoney;
    private float goalTime;
    private boolean isFondoEmergenza;
    private boolean isFondoAuto_Casa;
    private boolean isFondoViaggi;
    private boolean isFondoInvestire;

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

    public boolean isFondoAuto_Casa() {
        return isFondoAuto_Casa;
    }

    public void setFondoAuto_Casa(boolean fondoAuto_Casa) {
        isFondoAuto_Casa = fondoAuto_Casa;
    }

    public boolean isFondoViaggi() {
        return isFondoViaggi;
    }

    public void setFondoViaggi(boolean fondoViaggi) {
        isFondoViaggi = fondoViaggi;
    }

    public boolean isFondoInvestire() {
        return isFondoInvestire;
    }

    public void setFondoInvestire(boolean fondoInvestire) {
        isFondoInvestire = fondoInvestire;
    }
}
