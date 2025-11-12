import java.net.*;
import java.io.*;

/**
 * Diese Klasse bearbeitet einen RSS-Feed und gibt die XML repräsentation auf der Konsole aus.
 * @author Max Gebert
 */
public class Main {

    public static void main(String[] args){

        try{
            URL url = new URL("https://rss.dw.com/xml/rss-de-all");
            URLConnection connection = url.openConnection();
            connection.setDoInput(true);
            InputStream inStream = connection.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inStream));

            String line;

            while ((line = input.readLine()) != null){
                System.out.println(line);
            }
        } catch (Exception e){
            System.out.println("Folgender Fehler ist aufgetreten: " + e);
        }
    }
}

