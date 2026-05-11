package com.gomiero.progettonegomiero.models;

import com.gomiero.progettonegomiero.models.contabilities.MeseCountability;

import java.util.ArrayList;
import java.util.Date;

public class Utente {
    private String username;
    private String password;
    private Note notesUtente;
    private PersonalData personalData;
    private MeseCountability contabilities;



    public Utente(String email, String password) {
        this.username = email;
        this.password = password;
        contabilities = new MeseCountability();
    }
    public boolean isFormSetted(){
        if (personalData != null){
            return true;
        }
        return false;
    }
    public void setFormData(PersonalData personalData1){
        this.personalData = personalData1;
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

    public MeseCountability getContabilities() {
        return contabilities;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setContabilities(MeseCountability contabilities) {
        this.contabilities = contabilities;
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
        return username + " " + password;
    }


}
