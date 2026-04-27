package com.gomiero.progettonegomiero.models;

import java.util.ArrayList;
import java.util.Date;

public class Note {
    private ArrayList<String> titlePersonalNotes = new ArrayList<String>();;
    private ArrayList<String> personalNotes = new ArrayList<String>();;
    private ArrayList<Date> datesNotes = new ArrayList<Date>();

    public ArrayList<String> getPersonalNotes() {
        return personalNotes;
    }
    public ArrayList<String> getTitlePersonalNotes(){return titlePersonalNotes;}

    public ArrayList<Date> getDatesNotes() {
        return datesNotes;
    }

    public void setNota(String title,String nota){
        titlePersonalNotes.add(title);
        personalNotes.add(nota);
        datesNotes.add(new Date());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < titlePersonalNotes.size(); i++) {
            sb.append("Titolo: ").append(titlePersonalNotes.get(i)).append("\n");
            sb.append("Contenuto: ").append(personalNotes.get(i)).append("\n");
        }

        return sb.toString();
    }


}
