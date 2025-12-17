package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class WikipediaAPI {

    /**
     * Ruft die Wikipedia API ab, um eine ArrayListe relevanter Artikel Titel zu finden.
     * @param _suchbegriff Der Suchbegriff
     * @return Eine alphabetisch sortierte Liste von Titeln
     * @throws MyWebException Bei Fehlern beim Zugriff oder Parsen der Daten
     */
    public static ArrayList<String> fetchWikipediaTitles(String _suchbegriff) throws MyWebException {

        ArrayList<String> titles = new ArrayList<>();
        String encodedTerm = URLEncoder.encode(_suchbegriff, StandardCharsets.UTF_8);

        String apiUrl = "https://de.wikipedia.org/w/api.php?action=query&origin=*&format=json&generator=search&gsrnamespace=0&gsrlimit=10&gsrsearch=" + encodedTerm;

        try {
            URL url = new URL(apiUrl);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "WikiBooksApp/1.0 (Studentenprojekt)");

            try (InputStream in = connection.getInputStream()) {

                JSONParser parser = new JSONParser();

                JSONObject jsonResponse = (JSONObject) parser.parse(new InputStreamReader(in, StandardCharsets.UTF_8));

                JSONObject query = (JSONObject) jsonResponse.get("query");

                if (query == null) {
                    return titles;
                }

                JSONObject pages = (JSONObject) query.get("pages");

                if (pages != null) {
                    for (Object pageId : pages.keySet()) {
                        JSONObject page = (JSONObject) pages.get(pageId);
                        String title = (String) page.get("title");
                        if (title != null) {
                            titles.add(title);
                        }
                    }
                }

            } catch (IOException e) {
                throw new MyWebException("Fehler beim Zugriff auf die Wikipedia API.", e);
            } catch (ParseException e) {
                throw new MyWebException("Fehler beim Parsen der JSON Antwort von Wikipedia.", e);
            }

        } catch (Exception e) {
            throw new MyWebException("Ein unerwarteter Fehler beim Abrufen der Wikipediadaten.", e);
        }

        Collections.sort(titles);
        return titles;
    }
}
