package com.gomiero.progettonegomiero.models.contabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Questa classe gestisce i dati delle spese di una singola giornata.
 * Utilizza una struttura a mappa dove ogni categoria ha la sua lista di importi.
 */

/*TODO:
   -i dati devono esser presi ad ogni apertura del file, ogni dato.
 */
public class SpesaGiornaliera {
    // Mappa che associa una categoria (String) a una lista di importi (ArrayList di Float)
    private HashMap<String, ArrayList<Float>> spesaGiornaliera = new HashMap<>();

    // Variabile accumulatore per tenere traccia della somma totale di tutte le spese inserite
    private float sumOfDay = 0;

    // Mappa per memorizzare quanti elementi ci sono per ogni categoria
    private Map<String, Integer> conteggi;

    /**
     * Aggiunge un valore di spesa a una specifica categoria.
     * Se la categoria non esiste, la crea. Aggiorna automaticamente la somma totale.
     */
    public void addSpesaGiornalieraValues(String key, Float spesa) {
        // Se la chiave non esiste, crea un nuovo ArrayList, poi aggiunge il valore spesa
        spesaGiornaliera.computeIfAbsent(key, k -> new ArrayList<>()).add(spesa);
        // Aggiorna il totale complessivo giornaliero
        sumOfDay += spesa;
    }

    /**
     * Restituisce la lista di tutti gli importi associati a una specifica categoria.
     */
    public ArrayList<Float> getValoriPerCategoria(String key) {
        return spesaGiornaliera.get(key);
    }

    /**
     * Genera e restituisce una mappa che contiene il numero di spese effettuate per ogni categoria.
     * Esempio: {"Cibo": 3, "Trasporti": 1}
     */
    public Map<String, Integer> getHashCounterCategorie() {
        // Inizializza (o resetta) la mappa dei conteggi
        conteggi = new HashMap<>();
        // Cicla sulla mappa principale e per ogni chiave salva la dimensione (size) della lista
        spesaGiornaliera.forEach((chiave, lista) -> {
            conteggi.put(chiave, lista.size());
        });
        return conteggi;
    }

    /**
     * Calcola la media aritmetica di tutte le spese inserite in tutte le categorie.
     */
    public float getAverageSpesa() {
        int totaleElementi = 0;
        // Somma la dimensione di ogni lista presente nella mappa per ottenere il numero totale di spese
        for (ArrayList<Float> lista : spesaGiornaliera.values()) {
            totaleElementi += lista.size();
        }
        // Controllo per evitare la divisione per zero se non ci sono spese
        if (totaleElementi == 0) return 0;

        // Calcola il rapporto tra la somma totale e il numero di transazioni
        return sumOfDay / totaleElementi;
    }

    // Restituisce la somma totale giornaliera accumulata
    public float getSumOfDay() {
        return sumOfDay;
    }

    // Restituisce l'intera mappa delle spese
    public HashMap<String, ArrayList<Float>> getHashSpesa() {
        return spesaGiornaliera;
    }

    public void setSumOfDay(float sumOfDay) {
        this.sumOfDay = sumOfDay;
    }

    /**
     * Permette di sovrascrivere l'intera mappa delle spese.
     */
    public void setSpesaGiornaliera(HashMap<String, ArrayList<Float>> spesaGiornaliera) {
        this.spesaGiornaliera = spesaGiornaliera;

    }
}
