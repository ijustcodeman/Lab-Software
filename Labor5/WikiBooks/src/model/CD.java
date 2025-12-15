package model;

import java.io.Serializable;

/**
 * Die Klasse model.CD ist eine Unterklasse von Medium
 * und repräsentiert eine Musik-model.CD.
 * Eine model.CD hat neben dem Titel (geerbt von Medium) auch ein Label
 * und einen Künstler.
 * @author Max Gebert, 21513
 */
public class CD extends Medium implements Serializable{

    /**
     * Label der model.CD.
     */
    private String label;

    /**
     * Künstler der model.CD.
     */
    private String kuenstler;

    /**
     * Gesamtdauer der model.CD.
     */
    private double gesamtdauer;

    /**
     * Altersfreigabe der model.CD.
     */
    private int altersfreigabe;

    /**
     * Status der model.CD.
     */
    private boolean status;

    /**
     * Konstruktor zum Erstellen einer model.CD.
     * @param _titel Titel der model.CD
     * @param _label Label der model.CD
     * @param _kuenstler Künstler der model.CD
     * @param _gesamtdauer Gesamtdauer der model.CD
     * @param _altersfreigabe Altersfreigabe der model.CD
     */
    public CD(String _titel, String _label, String _kuenstler, double _gesamtdauer, int _altersfreigabe){
        super(_titel);

        if ((_label == null || _label.isBlank()) || (_kuenstler == null || _kuenstler.isBlank())){
            throw new IllegalArgumentException("Bitte korrekte Parameter übergeben.");
        }

        if (_gesamtdauer < 0 || _altersfreigabe < 0){
            throw new IllegalArgumentException("Du darfst keine negativen Zahlen als Parameter übergeben.");
        }
        this.label = _label;
        this.kuenstler = _kuenstler;
        this.gesamtdauer = _gesamtdauer;
        this.altersfreigabe = _altersfreigabe;
        this.status = true;
    }

    /**
     * Gibt das Label der model.CD zurück.
     * @return Label der model.CD
     */
    public String getLabel(){
        return this.label;
    }

    /**
     * Setzt das Label der model.CD.
     * @param _label Das neue Label der model.CD
     */
    public void setLabel(String _label){
        this.label = _label;
    }

    /**
     * Gibt den Künstler der model.CD zurück.
     * @return Künstler der model.CD
     */
    public String getKuenstler(){
        return this.kuenstler;
    }

    /**
     * Setzt den Künstler der model.CD.
     * @param _kuenstler Der neue Künstler der model.CD
     */
    public void setKuenstler(String _kuenstler){
        this.kuenstler = _kuenstler;
    }

    /**
     * Gibt die Gesamtdauer der model.CD zurück.
     * @return Gesamtdauer der model.CD
     */
    public double getGesamtdauer(){
        return this.gesamtdauer;
    }

    /**
     * Setzt die Gesamtdauer der model.CD.
     * @param _gesamtdauer Die neue Gesamtdauer der model.CD
     */
    public void setGesamtdauer(double _gesamtdauer){
        this.gesamtdauer = _gesamtdauer;
    }

    /**
     * Gibt die Altersfreigabe der model.CD zurück.
     * @return Altersfreigabe der model.CD
     */
    public int getAltersfreigabe(){
        return this.altersfreigabe;
    }

    /**
     * Setzt die Altersfreigabe der model.CD.
     * @param _altersfreigabe Die neue Altersfreigabe der model.CD
     */
    public void setAltersfreigabe(int _altersfreigabe){
        this.altersfreigabe = _altersfreigabe;
    }

    /**
     * Gibt eine textuelle Darstellung von der model.CD zurück.
     * Sie überschreibt die abstrakte Methode aus der Oberklasse Medium.
     * @return Ein String mit Titel, Label, Künstler, Gesamtdauer und Altersfreigabe der model.CD
     */
    @Override
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder(super.calculateRepresentation());
        sb.append("Label: ").append(getLabel()).append("\n");
        sb.append("Kuenstler: ").append(getKuenstler()).append("\n");
        sb.append("Gesamtdauer: ").append(getGesamtdauer()).append("\n");
        sb.append("Altersfreigabe: ").append(getAltersfreigabe()).append("\n");
        return sb.toString();
    }

    @Override
    public void ausleihen() {
        if (this.status){
            this.status = false;
        }
        else{
            System.out.println("Diese model.CD ist bereits ausgeliehen.");
        }
    }

    @Override
    public void rueckgabe() {
        if (!this.status){
            this.status = true;
        }
        else{
            System.out.println("Du kannst eine model.CD die du nicht besitzt, nicht zurückgeben.");
        }
    }

    @Override
    public void verlaengern() {
        if (!this.status){
            System.out.println("Du kannst den Eigentumszeitraum einer nicht besitzenden model.CD nicht verlängern.");
        }
        else{
            System.out.println("Eigentumszeitraum verlängert.");
        }
    }
}