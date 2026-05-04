package com.gomiero.progettonegomiero.models;

import java.util.ArrayList;
import java.util.Date;

public class Utente {
    private String username;
    private String password;
    private Date dataCreazioneUtente = new Date();
    private Note notesUtente;
    private FormData formData;



    public Utente(String email, String password) {
        this.username = email;
        this.password = password;
        this.notesUtente = new Note();
    }
    public boolean isFormSetted(){
        if (formData != null){
            return true;
        }
        return false;
    }
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }


    public ArrayList<String> getNotes(){
        return notesUtente.getPersonalNotes();
    }
    public ArrayList<String> getTitoloNotes(){
    for (String t : notesUtente.getTitlePersonalNotes()){

    }
    return notesUtente.getPersonalNotes();
    }
    public ArrayList<Date> getDates(){
        return notesUtente.getDatesNotes();
    }

    public void setNotesUtente(String title, String nota){
        notesUtente.setNota(title,nota);
    }
    public String showNotes(){
         return notesUtente.toString();
    }


    @Override
    public String toString(){
        return username + " " + password + " " + dataCreazioneUtente;
    }


}
