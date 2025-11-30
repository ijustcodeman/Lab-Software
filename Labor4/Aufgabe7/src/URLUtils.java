import java.net.URL;

/**
 * Die Klasse URLUtils enthält eine statische Hilfsmethode zur Validierung von einer URL.
 */
public class URLUtils {

    /**
     * Prüft, ob eine URL gültig ist.
     * @param _urlString die zu prüfende URL
     * @return true, wenn die URL gültig ist, sonst false
     */
    public static boolean checkURL(String _urlString) {
        try {
            URL url = new URL(_urlString);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
