package com.gomiero.progettonegomiero.classi;

import java.util.ArrayList;

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
    public int EsisteUtente(String email,String password){
        for (Utente t : utenti) {
            if (t.getEmail().equals(email)) {
              return 1;
            }
        }
        return 0;
    }

}
