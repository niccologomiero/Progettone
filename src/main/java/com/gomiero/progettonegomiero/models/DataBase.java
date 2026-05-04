package com.gomiero.progettonegomiero.models;

import java.util.ArrayList;

public class DataBase {

    private final ArrayList<Utente> utenti = new ArrayList<>();

    public ArrayList<Utente> getUtenti (){
        return utenti;
    }
    public void addUtente(Utente t){
        utenti.add(t);
    }
    public int ControlloUtente(String email,String password) {
        for (Utente t : utenti) {
            if (t.getUsername().equals(email)) {
                if (t.getPassword().equals(password)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }
        return 2;
    }
    public int EsisteUtente(String email){
        for (Utente t : utenti) {
            if (t.getUsername().equals(email)) {
              return 1;
            }
        }
        return 0;
    }
    public Utente getUtente(String username){
        for (Utente t : utenti){
            if (t.getUsername().equals(username)){
                return t;
            }
        }
        return null;
    }

}
