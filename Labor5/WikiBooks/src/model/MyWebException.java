package model;

import java.io.IOException;

/**
 * Spezielle Ausnahme, die ausgelöst wird, wenn beim Abrufen oder Parsen
 * der WikiBook-Metadaten von der Export-URL ein Fehler auftritt.
 */
public class MyWebException extends IOException {

    /**
     * Konstruktor, der die Fehlermeldung und die ursprüngliche Ursache kapselt.
     * @param message Die spezifische Fehlermeldung
     * @param cause Die ursprüngliche Exception, die diesen Fehler ausgelöst hat
     */
    public MyWebException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor, der nur die Fehlermeldung entgegennimmt.
     * @param message Die spezifische Fehlermeldung
     */
    public MyWebException(String message) {
        super(message);
    }
}
