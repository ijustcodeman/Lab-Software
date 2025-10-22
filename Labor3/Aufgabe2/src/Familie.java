import java.util.ArrayList;

/**
 * Diese Klasse repräsentiert eine Familie, welche aus Vater, Mutter und Kindern bestehen kann.
 * @author Max Gebert, 21513
 */
public class Familie {

    /**
     * Liste zum speichern aller Familienmitglieder
     */
    private ArrayList<String> mitglieder;

    /**
     * Wahrheitswert zum Prüfen, ob ein Kind einen Vater hat
     */
    private boolean hatVater;

    /**
     * Wahrheitswert zum Prüfen, ob ein Kind eine Mutter hat.
     */
    private boolean hatMutter;

    /**
     * Konstruktor zum erstellen einer Familie mit nur einen Elternteil.
     * @param _elternTeil Name vom Elternteil
     * @param istVater true für Vater, false für Mutter
     */
    public Familie(String _elternTeil, boolean istVater){
        if (_elternTeil == null || _elternTeil.isEmpty()){
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        mitglieder = new ArrayList<>();
        mitglieder.add(_elternTeil);

        this.hatVater = istVater;
        this.hatMutter = !istVater;
    }

    /**
     * Konstruktor zum erstellen einer Familie mit Vater und Mutter.
     * @param _vater Name vom Vater
     * @param _mutter Name der Mutter
     */
    public Familie(String _vater, String _mutter){
        if (_vater == null || _mutter == null || _vater.isEmpty() || _mutter.isEmpty()){
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        mitglieder = new ArrayList<>();
        mitglieder.add(_vater);
        mitglieder.add(_mutter);

        this.hatVater = true;
        this.hatMutter = true;
    }

    /**
     * Methode, um ein Knd zur Familie hinzuzufügen.
     * @param _kind Name vom Kind
     */
    public void addKind(String _kind){
        if (_kind == null){
            throw new IllegalArgumentException("Der Name darf nicht Null sein.");
        }
        this.mitglieder.add(_kind);
    }

    /**
     * Enum mit Vater, Mutter und Kind.
     */
    public enum Familienmitglied{
        VATER,
        MUTTER,
        KINDER;
    }

    /**
     * Gibt ein Familienmitglied der Familie als String zurück.
     * @param mitglied Das Familienmitglid
     * @return Das Familienmitglied als String
     */
    public String getMitglied(Familienmitglied mitglied){

        switch (mitglied){
            case VATER:
                if (hatVater){
                    return mitglieder.get(0);
                }
                else{
                    return "";
                }

            case MUTTER:
                if (hatMutter){
                    if (hatVater){
                        return mitglieder.get(1);
                    }
                    else{
                        return mitglieder.get(0);
                    }
                }
                else{
                    return "";
                }

            case KINDER:
                int startIndex = hatMutter && hatVater ? 2 : 1;
                if (mitglieder.size() <= startIndex){
                    return "";
                }
                StringBuilder kinder = new StringBuilder();
                for (int i = startIndex; i < mitglieder.size(); i++) {
                    kinder.append(mitglieder.get(i));
                    if (i < mitglieder.size() - 1) {
                        kinder.append(", ");
                    }
                }
                return kinder.toString();

            default:
                return "";
        }
    }
}
