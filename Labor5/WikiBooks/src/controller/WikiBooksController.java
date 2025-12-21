package controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Der Controller für die WikiBooks Anwendung.
 * Diese Klasse verbindet die Benutzeroberfläche mit der Logik.
 * Sie verwaltet die Suche nach WikiBooks, die Integration der Wikipedia API,
 * die Historien Navigation sowie die Verwaltung des lokalen Zettelkastens.
 * @author Max Gebert, 21513
 */
public class WikiBooksController {

    /**
     * Eingabefeld für den Titel des gesuchten WikiBooks.
     */
    public TextField searchTitle;

    /**
     * Speichert den aktuell extrahierten Titel aus dem Suchfeld für die weitere Verarbeitung.
     */
    public String extractedTitle = "";

    /**
     * Anzeige Komponente für die Webinhalte des gewählten WikiBooks.
     */
    public WebView viewBook;

    /**
     * Label zur Anzeige des Benutzernamens oder der IP des letzten Bearbeiters.
     */
    public Label lastUsernameValue;

    /**
     * Label zur Anzeige des Zeitpunkts der letzten Änderung am WikiBook.
     */
    public Label lastChangeValue;

    /**
     * Label zur Anzeige der zugeordneten Regale also Kategorien des WikiBooks.
     */
    public Label regalValue;

    /**
     * Liste zur Anzeige der Titel der im Zettelkasten gespeicherten Medien.
     */
    public ListView<String> viewMedien = new ListView<>();

    /**
     * Liste zur Anzeige von gefundenen Synonymen oder verwandten Titeln aus Wikipedia.
     */
    public ListView<String> viewSynonyme = new ListView<>();

    /**
     * Schaltfläche zum Starten einer gezielten Wikipedia Suche basierend auf der Auswahl.
     */
    public Button searchWikipediaButton;

    /**
     * Schaltfläche zur Navigation zum vorherigen Eintrag in der Suchhistorie.
     */
    public Button backButton;

    /**
     * Schaltfläche zur Navigation zum nächsten Eintrag in der Suchhistorie.
     */
    public Button forwardButton;

    /**
     * ComboBox zur Anzeige und Auswahl der bisherigen Suchhistorie in umgekehrter Reihenfolge.
     */
    public ComboBox<String> navigationComboBox = new ComboBox<>();

    /**
     * Der Wurzel Container des Layouts, wird primär für globale Eventfilter genutzt.
     */
    public VBox rootContainer;

    /**
     * Die Menüleiste der Anwendung.
     */
    public MenuBar mainMenuBar;

    /**
     * Schaltfläche zum Auslösen der Suche basierend auf dem aktuellen Text im Suchfeld.
     */
    public Button searchButton;

    /**
     * Schaltfläche für den Export der Daten.
     */
    public Button exportButton;

    /**
     * Das zentrale Datenmodell, dass die Liste aller Medien verwaltet.
     */
    private Zettelkasten zettelkasten = new Zettelkasten();

    /**
     * Komponente für die binäre Serialisierung, um den Zettelkasten dauerhaft zu speichern.
     */
    private BinaryPersistency binaryPersistency = new BinaryPersistency();

    /**
     * Standard Dateiname für das Speichern und Laden des Zettelkastens.
     */
    private final String DEFAULT_FILENAME = "zettelkasten_data";

    /**
     * Der aktuelle Index innerhalb der Suchhistorie (savedTitles).
     * Er dient der Steuerung der Vor und Zurück Navigation.
     */
    private int currentIndex = -1;

    /**
     * Liste der erfolgreich gesuchten Titel zur Realisierung der Navigationshistorie.
     */
    private ArrayList<String> savedTitles = new ArrayList<>();

    /**
     * Flag zur Vermeidung von Endlosschleifen bei automatischen Updates der ComboBox.
     * Verhindert, dass ein programmatisches Setzen des Index ein neues Event auslöst.
     */
    private boolean isInternalUpdate = false;

    /**
     * Initialisiert den Controller nach dem Laden der FXML Datei.
     * Konfiguriert Event Handler für die Suche, Navigation und Listeninteraktionen.
     * Beinhaltet die Logik für die Tabreihenfolge und globale Shortcuts.
     */
    public void initialize() {
        searchTitle.setOnAction(this::onClickSearchTitle);
        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getWikiBookLink());
        backButton.setDisable(true);
        forwardButton.setDisable(true);
        navigationComboBox.setOnAction(this::onNavigationChanged);

        viewSynonyme.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String selected = viewSynonyme.getSelectionModel().getSelectedItem();
                if (selected != null && !selected.isEmpty() && !selected.equals("<keine>")) {
                    searchTitle.setText(selected);
                    onClickSearchTitle(new ActionEvent());
                }
            }
        });

        exportButton.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.TAB && !event.isShiftDown()) {
                mainMenuBar.requestFocus();
                event.consume();
            }
        });

        mainMenuBar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB && !event.isShiftDown()) {
                if (!backButton.isDisable()) {
                    backButton.requestFocus();
                } else if (!navigationComboBox.isDisable()) {
                    navigationComboBox.requestFocus();
                } else {
                    forwardButton.requestFocus();
                }
                event.consume();
            }
            else if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
                onAboutMenuAction(new ActionEvent());
                event.consume();
            }
        });

        navigationComboBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
                if (!navigationComboBox.isShowing()) {
                    navigationComboBox.show();
                    event.consume();
                }
            }
        });

        viewMedien.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedTitle = viewMedien.getSelectionModel().getSelectedItem();
                if (selectedTitle != null && !selectedTitle.isEmpty()) {
                    searchTitle.setText(selectedTitle);
                    onClickSearchTitle(new ActionEvent());
                }
            }
        });

        viewMedien.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String selectedTitle = viewMedien.getSelectionModel().getSelectedItem();
                if (selectedTitle != null) {
                    searchTitle.setText(selectedTitle);
                    onClickSearchTitle(new ActionEvent());
                }
            }
        });

        // sicherstellen, dass die szene geladen ist
        Platform.runLater(() -> {
            rootContainer.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.F1) {
                    onAboutMenuAction(new ActionEvent());
                    event.consume();
                }
            });
        });
    }

    /**
     * Verarbeitet die Suche nach einem Buchtitel.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickSearchTitle(ActionEvent actionEvent) {
        extractedTitle = searchTitle.getText().trim();
        if (extractedTitle.isEmpty()){
            warningEmptyTitle();
            return;
        }

        if (currentIndex >= 0 && savedTitles.get(currentIndex).equals(extractedTitle)) {
            loadAndFetchEverything(extractedTitle);
            return;
        }

        if (currentIndex < savedTitles.size() - 1) {
            savedTitles = new ArrayList<>(savedTitles.subList(0, currentIndex + 1));
        }

        savedTitles.add(extractedTitle);
        currentIndex = savedTitles.size() - 1;

        loadAndFetchEverything(extractedTitle);

        updateNavigationButtons();
        updateCombobox();

    }

    /**
     * Versucht, das aktuell im Suchfeld stehende WikiBook permanent zum Zettelkasten hinzuzufügen.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickAddWikiBook(ActionEvent actionEvent) {
        extractedTitle = searchTitle.getText().trim();

        if (extractedTitle.isEmpty()){
            warningEmptyTitle();
            return;
        }
        boolean success = zettelkasten.addWikiBook(extractedTitle);

        if (!success){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER");
            alert.setHeaderText("Hinzufügen fehlgeschlagen");
            alert.setContentText("Das folgende WikiBook konnte nicht hinzugefügt werden: " + extractedTitle);
            alert.showAndWait();
        }

        else{
            updateMediaListView();
        }

    }

    /**
     * Wechselt die Sortierreihenfolge der Medienliste zwischen AUFSTEIGEND und ABSTEIGEND.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickSortWikiBook(ActionEvent actionEvent) {
        int anzahlMedien = zettelkasten.getMyZettelkasten().size();

        if (anzahlMedien < 2) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sortieren nicht möglich");
            alert.setHeaderText(null);

            String nachricht;
            if (anzahlMedien == 0) {
                nachricht = "Der Zettelkasten ist leer. Es gibt nichts zu sortieren.";
            } else {
                nachricht = "Es befindet sich nur ein Medium im Zettelkasten. Eine Sortierung ist erst ab zwei Medien sinnvoll.";
            }

            alert.setContentText(nachricht);
            alert.showAndWait();
            return;
        }

        String sortOrder = zettelkasten.getCurrentSortOrder();
        if (sortOrder == null || sortOrder.equals("ABSTEIGEND")){
            zettelkasten.sort("AUFSTEIGEND");
        } else {
            zettelkasten.sort("ABSTEIGEND");
        }
        updateMediaListView();
    }

    /**
     * Entfernt das aktuell in der Medienliste markierte Element aus dem Zettelkasten.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickDeleteWikiBook(ActionEvent actionEvent) {
        String selectedTitle = viewMedien.getSelectionModel().getSelectedItem();

        if (selectedTitle == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Löschen nicht möglich");
            alert.setHeaderText(null);
            alert.setContentText("Bitte wählen Sie erst ein Medium aus der Liste 'Medien' aus.");
            alert.showAndWait();
            return;
        }

        boolean success = zettelkasten.dropMedium(selectedTitle, true, 0);

        if (success) {
            updateMediaListView();
            viewMedien.getSelectionModel().clearSelection();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setContentText("Das Medium konnte nicht aus dem Zettelkasten gelöscht werden.");
            alert.showAndWait();
        }
    }

    /**
     * Speichert den aktuellen Zustand des Zettelkastens binär ab.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickSave(ActionEvent actionEvent) {

        if (zettelkasten.getMyZettelkasten().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Speichern Fehlgeschlagen");
            alert.setHeaderText(null);
            alert.setContentText("Du kannst keinen leeren Zettelkasten speichern.");
            alert.showAndWait();
            return;
        }
        try {
            binaryPersistency.save(zettelkasten, DEFAULT_FILENAME);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Der Zettelkasten wurde erfolgreich unter '" + DEFAULT_FILENAME + ".ser' gespeichert.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER beim Speichern");
            alert.setHeaderText("Konnte Daten nicht serialisieren.");
            alert.setContentText("Fehler: " + e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Lädt einen zuvor gespeicherten Zettelkasten aus einer binären Datei.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickLoad(ActionEvent actionEvent) {
        Zettelkasten loadedZk = binaryPersistency.load(DEFAULT_FILENAME);

        if (loadedZk != null) {
            this.zettelkasten = loadedZk;

            updateMediaListView();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Laden erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Der Zettelkasten wurde erfolgreich von '" + DEFAULT_FILENAME + ".ser' geladen.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER beim Laden");
            alert.setHeaderText("Laden fehlgeschlagen.");
            alert.setContentText("Die Datei '" + DEFAULT_FILENAME + ".ser' konnte nicht gefunden oder gelesen werden.");
            alert.showAndWait();
        }
    }

    /**
     * Platzhalter Methode für den Import von Daten.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickImport(ActionEvent actionEvent) {

        String errorText = "Die Importfunktion wird in dieser Version nicht unterstützt.";

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("FEHLER: Funktion nicht implementiert");
        alert.setHeaderText("Import fehlgeschlagen");
        alert.setContentText(errorText);
        alert.showAndWait();

        throw new UnsupportedOperationException(errorText);
    }

    /**
     * Platzhalter Methode für den Export von Daten.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickExport(ActionEvent actionEvent) {

        String errorText = "Die Exportfunktion wird in dieser Version nicht unterstützt.";

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("FEHLER: Funktion nicht implementiert");
        alert.setHeaderText("Export fehlgeschlagen");
        alert.setContentText(errorText);
        alert.showAndWait();

        throw new UnsupportedOperationException(errorText);
    }

    /**
     * Navigiert in der Suchhistorie einen Schritt zurück.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickBack(ActionEvent actionEvent) {
        if (currentIndex > 0) {
            currentIndex--;
            String title = savedTitles.get(currentIndex);
            searchTitle.setText(title);
            loadAndFetchEverything(title);
            updateNavigationButtons();
            updateCombobox();
        }
    }

    /**
     * Navigiert in der Suchhistorie einen Schritt vor.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickForward(ActionEvent actionEvent) {
        if (currentIndex < savedTitles.size() - 1) {
            currentIndex++;
            String title = savedTitles.get(currentIndex);
            searchTitle.setText(title);
            loadAndFetchEverything(title);
            updateNavigationButtons();
            updateCombobox();
        }
    }

    /**
     * Reagiert auf Mausklicks in der Synonyme Liste.
     * @param mouseEvent Das auslösende Mausereignis
     */
    public void onSynonymListViewClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            String selectedTitle = viewSynonyme.getSelectionModel().getSelectedItem();

            if (selectedTitle != null && !selectedTitle.isEmpty()) {
                onClickSearchWikipedia(null);
            }
        }
    }

    /**
     * Startet eine Suche basierend auf dem in der Wikipedia Vorschlagsliste ausgewählten Eintrag.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onClickSearchWikipedia(ActionEvent actionEvent) {
        String selectedTitle = viewSynonyme.getSelectionModel().getSelectedItem();

        if (selectedTitle == null || selectedTitle.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNUNG");
            alert.setHeaderText(null);
            alert.setContentText("Bitte wählen Sie zuerst einen Wikipedia-Titel aus der Liste aus.");
            alert.showAndWait();
            return;
        }
        searchTitle.setText(selectedTitle);

        onClickSearchTitle(actionEvent);
    }

    /**
     * Ermöglicht das direkte Springen zu einem Titel aus der Historie.
     * @param actionEvent Das auslösende Ereignis der ComboBox
     */
    public void onNavigationChanged(ActionEvent actionEvent) {
        if (isInternalUpdate) return;

        int selectedComboIndex = navigationComboBox.getSelectionModel().getSelectedIndex();

        if (selectedComboIndex != -1) {
            currentIndex = (savedTitles.size() - 1) - selectedComboIndex;

            String title = savedTitles.get(currentIndex);
            searchTitle.setText(title);

            loadAndFetchEverything(title);
            updateNavigationButtons();
        }
    }

    /**
     * Zeigt einen Informationsdialog ("Über dieses Programm") an.
     * @param actionEvent Das auslösende Ereignis
     */
    public void onAboutMenuAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Über dieses Programm");
        alert.setHeaderText(null);

        String content = "Alle redaktionellen Inhalte stammen von den Internetseiten der Projekte Wikibooks und Wortschatz.\n\n" +
                "Die von Wikibooks bezogenen Inhalte unterliegen seit dem 22. Juni 2009 unter der Lizenz CC-BY-SA 3.0 " +
                "Unported zur Verfügung. Eine deutschsprachige Dokumentation für Weiternutzer findet man in den " +
                "Nutzungsbedingungen der Wikimedia Foundation. Für alle Inhalte von Wikibooks galt bis zum 22. Juni " +
                "2009 standardmäßig die GNU FDL (GNU Free Documentation License, engl. für GNU-Lizenz für freie " +
                "Dokumentation). Der Text der GNU FDL ist unter " +
                "http://de.wikipedia.org/wiki/Wikipedia:GNU_Free_Documentation_License verfügbar.\n\n" +
                "Die von Wortschatz (http://wortschatz.uni-leipzig.de/) oder Wikipedia (www.wikipedia.de) bezogenen " +
                "Inhalte sind urheberrechtlich geschützt. Sie werden hier für wissenschaftliche Zwecke eingesetzt und " +
                "dürfen darüber hinaus in keiner Weise genutzt werden.\n\n" +
                "Dieses Programm ist nur zur Nutzung durch den Programmierer selbst gedacht. Dieses Programm dient " +
                "der Demonstration und dem Erlernen von Prinzipien der Programmierung mit Java. Eine Verwendung " +
                "des Programms für andere Zwecke verletzt möglicherweise die Urheberrechte der Originalautoren der " +
                "redaktionellen Inhalte und ist daher untersagt.";

        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setPrefWidth(500);
        textArea.setPrefHeight(300);

        alert.getDialogPane().setContent(textArea);

        alert.showAndWait();
    }

    /**
     * Startet einen Hintergrund Thread, um Metadaten zu einem WikiBook abzurufen.
     * @param _title Der Titel des abzurufenden WikiBooks
     */
    private void startWikiBookFetchTask(String _title) {
        lastUsernameValue.setText("Wird geladen...");
        lastChangeValue.setText("Wird geladen...");
        regalValue.setText("Wird geladen...");
        searchTitle.setDisable(true);

        Task<WikiBook> fetchTask = new Task<>() {
            @Override
            protected WikiBook call() throws Exception {
                WikiBook book = zettelkasten.fetchWikiBook(_title);

                if (book == null) {
                    throw new Exception("Das WikiBook '" + _title + "' konnte nicht abgerufen werden.");
                }
                return book;
            }
        };

        fetchTask.setOnSucceeded(event -> {
            WikiBook resultBook = fetchTask.getValue();
            updateUI(resultBook);
            searchTitle.setDisable(false);
        });

        fetchTask.setOnFailed(event -> {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER");
            alert.setHeaderText(null);
            alert.setContentText(fetchTask.getException().getMessage());
            alert.showAndWait();

            System.err.println("Fehler beim Abrufen des WikiBooks: " + fetchTask.getException().getMessage());
            lastUsernameValue.setText("Fehler");
            lastChangeValue.setText("Fehler");
            regalValue.setText("Fehler");
            searchTitle.setDisable(false);
        });

        new Thread(fetchTask).start();
    }

    /**
     * Startet einen Hintergrund Thread, um passende Wikipedia Titel abzurufen.
     * @param _searchKeyword Das Stichwort, nach dem in der Wikipedia API gesucht werden soll.
     */
    private void startWikipediaFetchTask(String _searchKeyword) {
        if (_searchKeyword.trim().isEmpty()) {
            return;
        }

        viewSynonyme.setDisable(false);
        searchWikipediaButton.setDisable(false);

        Task<ArrayList<String>> fetchTask = new Task<>() {
            @Override
            protected ArrayList<String> call() throws Exception {
                return WikipediaAPI.fetchWikipediaTitles(_searchKeyword);
            }
        };

        fetchTask.setOnSucceeded(event -> {
            ArrayList<String> result = fetchTask.getValue();
            viewSynonyme.getItems().clear();

            if (result == null || result.isEmpty()){
                viewSynonyme.getItems().add("<keine>");
                viewSynonyme.setDisable(true);
                searchWikipediaButton.setDisable(true);
            } else {
                viewSynonyme.setDisable(false);
                searchWikipediaButton.setDisable(false);
                viewSynonyme.getItems().addAll(result);
            }
        });

        fetchTask.setOnFailed(event -> {
            viewSynonyme.getItems().clear();
            viewSynonyme.getItems().add("<Fehler>");
            viewSynonyme.setDisable(true);
            searchWikipediaButton.setDisable(true);

            Throwable exception = fetchTask.getException();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER: Wikipedia Zugriff");
            alert.setHeaderText("Konnte Wikipediadaten nicht abrufen.");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();

            System.err.println("Fehler beim Abrufen der Wikipedia Daten: " + exception.getMessage());
            // zum debuggen
            exception.printStackTrace();
        });
        new Thread(fetchTask).start();
    }

    /**
     * Synchronisiert die Anzeige der Medienliste mit dem aktuellen Inhalt des Zettelkastens.
     * Löscht alle bisherigen Einträge in viewMedien und fügt die aktuellen Titel neu hinzu.
     */
    private void updateMediaListView() {
        viewMedien.getItems().clear();
        for (Medium medium : zettelkasten.getMyZettelkasten()) {
            viewMedien.getItems().add(medium.getTitel());
        }
    }

    /**
     * Aktualisiert die Detail Labels (Bearbeiter, Datum, Regal) in der Benutzeroberfläche.
     * @param _book Das WikiBook Objekt, dessen Daten angezeigt werden sollen, oder null
     */
    private void updateUI(WikiBook _book) {
        if (_book == null) {
            lastUsernameValue.setText("N/A");
            lastChangeValue.setText("N/A");
            regalValue.setText("N/A");
            return;
        }

        lastUsernameValue.setText(_book.getDisplayContributor());

        lastChangeValue.setText(_book.getFormattedLastModifiedDate());

        if (_book.getRegale().isEmpty()) {
            regalValue.setText("N/A");
        } else {
            regalValue.setText(String.join(", ", _book.getRegale()));
        }
    }

    /**
     * Steuert die Aktivierung der Navigationsbuttons.
     */
    private void updateNavigationButtons() {
        backButton.setDisable(currentIndex <= 0);
        forwardButton.setDisable(currentIndex >= savedTitles.size() - 1);
    }


    /**
     * Aktualisiert die Navigations ComboBox mit der aktuellen Historie.
     */
    private void updateCombobox() {
        isInternalUpdate = true;

        ArrayList<String> reversedTitles = new ArrayList<>(savedTitles);
        Collections.reverse(reversedTitles);

        navigationComboBox.getItems().setAll(reversedTitles);

        if (currentIndex != -1) {
            int comboIndex = (savedTitles.size() - 1) - currentIndex;
            navigationComboBox.getSelectionModel().select(comboIndex);
        }

        isInternalUpdate = false;
    }

    /**
     * Zeigt einen Standard Warndialog an, wenn versucht wird, eine Suche ohne Eingabe eines Titels zu starten.
     */
    public void warningEmptyTitle(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNUNG");
        alert.setHeaderText("Leerer Titel");
        alert.setContentText("Du musst ein Titel angeben.");
        alert.showAndWait();
    }

    /**
     * Methode zum Laden aller Daten eines WikiBooks.
     * @param _title Der rohe Titel des Buches, wie er vom Benutzer eingegeben oder aus einer Liste ausgewählt wurde
     */
    public void loadAndFetchEverything(String _title){
        String safeTitle = WikiBook.getURLTitle(_title);
        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getUrl(safeTitle));

        startWikiBookFetchTask(safeTitle);
        startWikipediaFetchTask(_title);
    }

}
