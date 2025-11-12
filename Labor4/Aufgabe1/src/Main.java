import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Main {

    private static PersonenContentHandler handler;

    public static void main(String[] args) {
        handler = new PersonenContentHandler();
        try {
            // XMLReader erzeugen
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();

            // Pfad zur XML Datei
            FileReader reader = new FileReader("C:\\Users\\Lenovo\\Desktop\\Studium\\Labor Software\\Labor4\\Aufgabe1\\personen.xml");
            InputSource inputSource = new InputSource(reader);

            // DTD kann optional übergeben werden
            // inputSource.setSystemId("X:\\personen.dtd");

            // PersonenContentHandler wird übergeben
            xmlReader.setContentHandler(handler);

            // Parsen wird gestartet
            xmlReader.parse(inputSource);

            readPersonFromConsole();

            System.out.println("Alle personen:");
            for (Person p : handler.getAllePersonen()) {
                System.out.println(p);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public static void readPersonFromConsole(){
        Scanner sc = new Scanner(System.in);
        Person newPerson = new Person();
        SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yyyy");

        newPerson.setId(handler.getAllePersonen().size() + 1);
        System.out.println("ID: " + newPerson.getId());


        System.out.print("Nachname: ");
        String name = sc.nextLine();
        newPerson.setName(checkInput(name));

        System.out.print("Vorname: ");
        String vorname = sc.nextLine();
        newPerson.setVorname(checkInput(vorname));

        Date geburtsdatum = null;
        while (geburtsdatum == null) {
            System.out.print("Geburtsdatum (Format dd.MM.yyyy): ");
            String dateString = sc.nextLine();

            if (checkInput(dateString).equals("Ungültig")) {
                System.out.println("Eingabe darf nicht leer sein. Bitte verwenden Sie dd.MM.yyyy.");
                continue;
            }

            try {
                geburtsdatum = datumsformat.parse(dateString);
                newPerson.setGeburtsdatum(geburtsdatum);
            } catch (ParseException e) {
                System.out.println("Ungültiges Datumsformat. Bitte verwenden Sie dd.MM.yyyy.");
            }
        }

            System.out.print("Postleitzahl: ");
            String postleitzahl = sc.nextLine();
            newPerson.setPostleitzahl(checkInput(postleitzahl));

            System.out.print("Ort: ");
            String ort = sc.nextLine();
            newPerson.setOrt(checkInput(ort));

            System.out.print("Hobby: ");
            String hobby = sc.nextLine();
            newPerson.setHobby(checkInput(hobby));

            System.out.print("Lieblingsgericht: ");
            String lieblingsgericht = sc.nextLine();
            newPerson.setLieblingsgericht(checkInput(lieblingsgericht));

            System.out.print("Lieblingsband: ");
            String lieblingsband = sc.nextLine();
            newPerson.setLieblingsband(checkInput(lieblingsband));

            handler.getAllePersonen().add(newPerson);
        }

    public static String checkInput(String _input){
        if (_input == null || _input.trim().isEmpty()){
            return "Ungültig";
        }
        else{
            return _input;
        }
    }
    }
