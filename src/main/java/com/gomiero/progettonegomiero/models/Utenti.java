package com.gomiero.progettonegomiero.models;

import java.util.ArrayList;
import java.util.Date;

public class Utenti {

    private final ArrayList<Utente> utenti = new ArrayList<>();

    public ArrayList<Utente> getUtenti (){
        return utenti;
    }
    public void addUtente(Utente t){
        utenti.add(t);
    }
    public int ControlloUtente(String email,String password) {
        for (Utente t : utenti) {
            if (t.getEmail().equals(email)) {
                if (t.getPassword().equals(password)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }
        return 2;
    }
    public Utente getUtente(Utente t){
        for (Utente t0 : utenti){
            if (t0.equals(t)){
                return t0;
            }
        }
        return null;
    }
    public int EsisteUtente(String email,String password){
        for (Utente t : utenti) {
            if (t.getEmail().equals(email)) {
              return 1;
            }
        }
        return 0;
    }

}
