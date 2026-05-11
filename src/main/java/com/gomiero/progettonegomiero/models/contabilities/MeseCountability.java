package com.gomiero.progettonegomiero.models.contabilities;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * CLASSE MeseCountability: Funge da "Contenitore Mensile".
 * Gestisce l'insieme di tutte le spese effettuate nei vari giorni di un mese specifico.
 */

/**TODO:
 *  -fare metodo getTotalSumForCategories per il mese FATTO
 * - rivedi bene i scopi che cerchi su HomeController.java
 */
public class MeseCountability {

    // Indica a quale periodo temporale (Anno/Mese) si riferiscono i dati
    private LocalDate currentYear;

    // Portfolio: La struttura dati principale (Nidificata).
    // Associa ogni giorno (Integer) a una lista di oggetti "SpesaGiornaliera".
    // L'ArrayList permette di avere più "sessioni" o "gruppi" di spesa nello stesso giorno.
    private HashMap<Integer, ArrayList<SpesaGiornaliera>> mensilitaDetails = new HashMap<>();

    // Accumulatore: tiene il conto in tempo reale di quanto speso in tutto il mese
    // per evitare di ricalcolarlo ogni volta (ottimizzazione delle prestazioni).
    private float speseTotaliMensili = 0;

    public MeseCountability(){
        /*
           COSTRUTTORE: Qui andrà la logica per "pescare" i dati dal Database
           all'avvio e popolare le mappe sopra citate.
        */
    }

    // Verifica se un giorno specifico ha già dei dati inseriti nel wallet.
    private boolean existsKey(int day){
        return mensilitaDetails.containsKey(day);
    }

    /**
     * REGISTRAZIONE DATI:
     * Inserisce un oggetto SpesaGiornaliera nel giorno corrispondente.
     * Se il giorno è nuovo, crea lo spazio in memoria (computeIfAbsent).
     */
    public void addSpeseMensili(int day, SpesaGiornaliera spesaGiornaliera) {
        mensilitaDetails.computeIfAbsent(day, tipoSpesa -> new ArrayList<>()).add(spesaGiornaliera);
        // Aggiorna istantaneamente il totale mensile
        this.speseTotaliMensili += spesaGiornaliera.getSumOfDay();
    }

    /**
     * CALCOLO DATE:
     * Recupera tutti i giorni del mese che hanno registrato attività.
     * Collections.sort assicura che i giorni siano in ordine (1, 2, 3...).
     */
    public ArrayList<Integer> getAllDates(){
        ArrayList<Integer> giorniSpese = new ArrayList<>(mensilitaDetails.keySet());
        Collections.sort(giorniSpese);
        return giorniSpese;
    }

    /**
     * ESTRAZIONE TOP CATEGORIE PER SPESA TOTALE:
     * Estrae le categorie in cui si è speso di più in assoluto, sommando i valori
     * della stessa categoria presenti in giorni diversi.
     *
     * @param limitCategories Numero di categorie con spesa maggiore da restituire.
     * @return Lista delle top N categorie ordinate per spesa totale decrescente.
     */
    public List<AbstractMap.SimpleEntry<String, Float>> getAverageBills(int limitCategories) {
        // 1. Raccolta dati iniziale (come nel tuo codice)
        HashMap<Integer, HashMap<String, Float>> temp = new HashMap<>();
        mensilitaDetails.forEach((k, lista) -> {
            for (SpesaGiornaliera sp : lista) {
                temp.put(k, sp.getMaxSumForCategoriesOfDay());
            }
        });

        // --- LOGICA STREAM CON RAGGRUPPAMENTO E SOMMA ---
        return temp.values().stream()
                // a. "Schiacciamo" tutte le HashMap interne in un unico flusso di Entry (Categoria=Valore)
                .flatMap(mappa -> mappa.entrySet().stream())

                // b. Raggruppiamo per nome categoria e sommiamo i valori (Float)
                // Collectors.groupingBy crea una mappa intermedia dove la chiave è il nome
                // e il valore è la somma di tutti i Float trovati per quella chiave.
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingDouble(Map.Entry::getValue)
                ))

                // c. Adesso abbiamo una Map<String, Double>. Riapriamo lo stream sulle sue Entry
                .entrySet().stream()

                // d. Convertiamo i Double (usati per la somma) di nuovo in Float per coerenza con il metodo
                // e ordiniamo in modo decrescente (dal valore più alto)
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().floatValue()))
                .sorted(Map.Entry.<String, Float>comparingByValue().reversed())

                // e. Prendiamo solo le prime N categorie sommate
                .limit(limitCategories)
                .toList();
    }
    public List<AbstractMap.SimpleEntry<String, Float>> getDetailsBills() {
        // 1. Raccolta dati iniziale (come nel tuo codice)
        HashMap<Integer, HashMap<String, Float>> temp = new HashMap<>();
        mensilitaDetails.forEach((k, lista) -> {
            for (SpesaGiornaliera sp : lista) {
                temp.put(k, sp.getMaxSumForCategoriesOfDay());
            }
        });

        // --- LOGICA STREAM CON RAGGRUPPAMENTO E SOMMA ---
        return temp.values().stream()
                // a. "Schiacciamo" tutte le HashMap interne in un unico flusso di Entry (Categoria=Valore)
                .flatMap(mappa -> mappa.entrySet().stream())

                // b. Raggruppiamo per nome categoria e sommiamo i valori (Float)
                // Collectors.groupingBy crea una mappa intermedia dove la chiave è il nome
                // e il valore è la somma di tutti i Float trovati per quella chiave.
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingDouble(Map.Entry::getValue)
                ))

                // c. Adesso abbiamo una Map<String, Double>. Riapriamo lo stream sulle sue Entry
                .entrySet().stream()

                // d. Convertiamo i Double (usati per la somma) di nuovo in Float per coerenza con il metodo
                // e ordiniamo in modo decrescente (dal valore più alto)
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().floatValue()))
                .sorted(Map.Entry.<String, Float>comparingByValue().reversed())

                // e. Prendiamo solo tutte categorie sommate
                .toList();
    }



    /**
     * ESTRAZIONE TOTALI GIORNALIERI:
     * Per un dato giorno, estrae la somma totale da ogni sessione presente nel mensilitaDetails.
     */
    public ArrayList<Float> getSpesaGiornaliera(int day) {
        ArrayList<Float> listaSpeseGiornaliere = new ArrayList<>();
        // Controllo di sicurezza: se il giorno non esiste nel mensilitaDetails, restituisce lista vuota
        ArrayList<SpesaGiornaliera> sessioniGiorno = mensilitaDetails.get(day);
        if (sessioniGiorno != null) {
            for (SpesaGiornaliera sessione : sessioniGiorno){
                listaSpeseGiornaliere.add(sessione.getSumOfDay());
            }
        }
        return listaSpeseGiornaliere;
    }

    /**
     * ESTRAZIONE MEDIE GIORNALIERE:
     * Simile al precedente, ma estrae il valore medio di spesa per ogni scontrino del giorno scelto.
     */
    public ArrayList<Float> getMediaGiornaliera(int day) {
        ArrayList<Float> listaMedieGiornaliere = new ArrayList<>();
        ArrayList<SpesaGiornaliera> sessioniGiorno = mensilitaDetails.get(day);
        if (sessioniGiorno != null) {
            for (SpesaGiornaliera sessione : sessioniGiorno){
                listaMedieGiornaliere.add(sessione.getAverageSpesa());
            }
        }
        return listaMedieGiornaliere;
    }

    /**
     * MEDIA MENSILE (LOGICA NIDIFICATA):
     * Scansiona ogni lista di ogni giorno per trovare la media di TUTTE le transazioni del mese.
     * Entra nel mensilitaDetails -> Prende la lista del giorno -> Analizza ogni oggetto SpesaGiornaliera.
     */
    public Float getMediaMensile(){
        float sommaTotale = 0;
        int conteggioOggetti = 0;

        // Ciclo esterno: scorre le liste dei vari giorni
        for (ArrayList<SpesaGiornaliera> listaGiorno : mensilitaDetails.values()){
            // Ciclo interno: scorre le sessioni dentro il singolo giorno
            for (SpesaGiornaliera sessione : listaGiorno){
                sommaTotale += sessione.getSumOfDay();
                conteggioOggetti++;
            }
        }
        // Se non ci sono spese, restituisce 0 per evitare errori matematici
        if (conteggioOggetti == 0) return 0f;
        return sommaTotale / conteggioOggetti;
    }

    // Metodi Getter e Setter standard per l'accesso alle variabili private
    public float getSpeseTotaliMensili(){
        return speseTotaliMensili;
    }

    public LocalDate getCurrentYear(){
        return currentYear;
    }

    public void setCurrentYear(LocalDate year){
        this.currentYear = year;
    }
}
