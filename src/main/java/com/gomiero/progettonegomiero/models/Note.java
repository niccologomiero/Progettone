package com.gomiero.progettonegomiero.models;

import java.util.ArrayList;
import java.util.Date;

public class Note {
    private ArrayList<String> personalNotes = new ArrayList<String>();;
    private ArrayList<Date> datesNotes = new ArrayList<Date>();

    public ArrayList<String> getPersonalNotes() {
        return personalNotes;
    }

    public ArrayList<Date> getDatesNotes() {
        return datesNotes;
    }

    public void setNota(String nota){
        personalNotes.add(nota);
        datesNotes.add(new Date());
    }


}
