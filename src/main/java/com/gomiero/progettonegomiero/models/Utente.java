package com.gomiero.progettonegomiero.classi;

public class Utente {
    private String email;
    private String password;

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public Utente(String email, String password) {
        this.email = email;
        this.password = password;
    }


    @Override
    public String toString(){
        return email + " " + password;
    }


}
