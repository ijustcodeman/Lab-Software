package student;

/**
 * Die Klasse ElektronischesMedium ist eine Unterklasse von Medium.
 * Sie besitzt einen Titel (von Medium) und eine URL.
 * @author Max Gebert, 21513
 */
public class ElektronischesMedium extends Medium {

    /**
     * URL des elektronischen Mediums.
     */
    private String url;

    private String dateiformat;

    private double groesse;

    private boolean ausgeliehen;

    private boolean verfuegbar;


    /**
     * Konstruktor zum Erstellen eines elektronischen Mediums.
     * @param _titel Titel des elektronischen Mediums
     * @param _url URL des elektronischen Mediums
     */
    public ElektronischesMedium(String _titel, String _url){
        super(_titel);
        setUrl(_url);
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
        if (URLUtils.checkURL(_url)){
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
        StringBuilder sb = new StringBuilder(super.calculateRepresentation());
        sb.append("URL: ").append(getUrl()).append("\n");
        return sb.toString();
    }
}
