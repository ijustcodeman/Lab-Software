package student;

import java.net.URL;

/**
 * Die Klasse ElektronischesMedium ist eine Unterklasse von Medium.
 * Sie besitzt einen Titel (von Medium) und eine URL.
 * @author Max Gebert, 21513
 */
public class ElektronischesMedium extends Medium {

    /**
     * Prüft, ob eine URL gültig ist.
     * @param _urlString die zu prüfende URL
     * @return true, wenn die URL gültig ist, sonst false
     */
    private static boolean checkURL(String _urlString) {
        try {
            URL url = new URL(_urlString);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * URL des elektronischen Mediums.
     */
    private String url;

    /**
     * Konstruktor zum Erstellen eines elektronischen Mediums.
     * @param _titel Titel des elektronischen Mediums
     * @param _url URL des elektronischen Mediums
     */
    public ElektronischesMedium(String _titel, String _url){
        super(_titel);
        this.url = _url;
    }

    /**
     * Gibt die URL des elektronischen Mediums zurück.
     * @return URL des elektronischen Mediums
     */
    public String getUrl(){
        return this.url;
    }

    /**
     * Setzt die URL des elektronischen Mediums, wenn sie gültig ist.
     * @param _url Neue URL des elektronischen Mediums
     */
    public void setUrl(String _url){
        if (checkURL(_url)){
            this.url = _url;
        }
        else{
            throw new IllegalArgumentException("Ungültige URL.");
        }
    }

    /**
     * Gibt eine textuelle Darstellung vom elektronischen Medium zurück.
     * Sie überschreibt die abstrakte Methode aus der Oberklasse Medium.
     * @return Ein String mit Titel und URL des elektronischen Mediums
     */
    @Override
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: ").append(getTitel()).append("\n");
        sb.append("URL: ").append(getUrl()).append("\n");
        return sb.toString();
    }
}
