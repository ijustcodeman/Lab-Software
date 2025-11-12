import java.util.Date;

public class Person {

    private int id;
    private String name;
    private String vorname;
    private Date geburtsdatum;
    private String postleitzahl;
    private String ort;

    // neu
    private String hobby;
    private String lieblingsgericht;
    private String lieblingsband;

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getHobby(){
        return this.hobby;
    }

    public void setHobby(String _hobby){
        this.hobby = _hobby;
    }

    public String getLieblingsgericht(){
        return this.lieblingsgericht;
    }

    public void setLieblingsgericht(String _lieblingsgericht){
        this.lieblingsgericht = _lieblingsgericht;
    }

    public String getLieblingsband(){
        return this.lieblingsband;
    }

    public void setLieblingsband(String _lieblingsband){
        this.lieblingsband = _lieblingsband;
    }

    @Override
    public String toString() {
        return "[[" + this.id + "] ["+ this.name + "] [" + this.vorname + "]" + " [" + this.ort
                + "] [" + this.postleitzahl + "] [" + this.geburtsdatum + " ] [" + this.hobby + "] [" + this.lieblingsgericht + "] [" + this.lieblingsband + "]";
    }
}
