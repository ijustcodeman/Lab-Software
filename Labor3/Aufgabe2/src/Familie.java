import java.util.ArrayList;

public class Familie {

    private ArrayList<String> mitglieder;

    private boolean hatVater;

    private boolean hatMutter;

    public Familie(String _elternTeil, boolean istVater){
        if (_elternTeil == null || _elternTeil.isEmpty()){
            throw new IllegalArgumentException("Der Name darf nicht leer sein.");
        }
        mitglieder = new ArrayList<>();
        mitglieder.add(_elternTeil);

        this.hatVater = istVater;
        this.hatMutter = !istVater;
    }

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
    public void addKind(String _kind){
        if (_kind == null){
            throw new IllegalArgumentException("Der Name darf nicht Null sein.");
        }
        this.mitglieder.add(_kind);
    }
    public enum Familienmitglied{
        VATER,
        MUTTER,
        KINDER;
    }

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
