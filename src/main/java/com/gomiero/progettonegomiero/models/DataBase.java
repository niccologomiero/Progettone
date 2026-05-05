package com.gomiero.progettonegomiero.models;

import java.util.HashMap;
import java.util.Map;

public class DataBase {

    //Username come chiave per l'accesso rapido
    private final Map<String, Utente> utenti = new HashMap<>();
    private Utente utenteLogged;
    private static DataBase instance;
    private static final int LOGIN_SUCCESS = 0;
    private static final int WRONG_PASSWORD = 1;
    private static final int USER_NOT_FOUND = 2;

    /**Dovrà caricare i dati letti dal file
     */
     public DataBase(){}
    /**
     * Salvataggio dei dati a fine sessione, scrittura del file;
     */
    public void saveDataToJSON(){

    }
    public static DataBase getInstance(){
        if (instance == null){
            instance = new DataBase();
        }
        return instance;
    }
    /**
     * Aggiunge un utente solo se l'username non è già stato preso.
     * @return true se la registrazione ha successo, false se l'utente esiste già.
     */
    public boolean registraUtente(Utente nuovoUtente) {
        if (nuovoUtente == null || nuovoUtente.getUsername() == null) return false;

        // Controllo critico: l'username esiste già?
        if (utenti.containsKey(nuovoUtente.getUsername())) {
            System.out.println("Errore: Username già esistente!");
            return false;
        }

        utenti.put(nuovoUtente.getUsername(), nuovoUtente);
        return true;
    }

    /**
     * Controllo Login rigoroso.
     * Restituisce un codice specifico per ogni scenario.
     */
    public int controlloUtente(String username, String password) {
        Utente t = utenti.get(username);

        // 1. L'utente non esiste proprio
        if (t == null) {
            return USER_NOT_FOUND;
        }

        // 2. L'utente esiste, controlliamo se la password corrisponde a QUELL'utente
        if (t.getPassword().equals(password)) {
            return LOGIN_SUCCESS;
        }

        // 3. L'username è giusto, ma la password è sbagliata
        return WRONG_PASSWORD;
    }

    // Metodo rapido per il controller della registrazione
    public boolean isUsernameDisponibile(String username) {
        return !utenti.containsKey(username);
    }

    public Utente getUtenteLogged() {
        return utenteLogged;
    }

    public Utente getUtente(String username){
        return utenti.get(username);
    }
    public void setUtenteLogged(Utente utente){
        this.utenteLogged = utente;
    }
}