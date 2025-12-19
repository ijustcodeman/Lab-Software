package controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.*;

import java.util.ArrayList;
import java.util.Collections;

public class WikiBooksController {

    public TextField searchTitle;

    public String extractedTitle = "";

    public WebView viewBook;

    public Label lastUsernameValue;
    public Label lastChangeValue;
    public Label regalValue;

    public ListView<String> viewMedien = new ListView<>();
    public ListView<String> viewSynonyme = new ListView<>();

    public Button searchWikipediaButton;
    public Button backButton;
    public Button forwardButton;

    public ComboBox<String> navigationComboBox = new ComboBox<>();

    private Zettelkasten zettelkasten = new Zettelkasten();

    private BinaryPersistency binaryPersistency = new BinaryPersistency();
    private final String DEFAULT_FILENAME = "zettelkasten_data";

    private int currentIndex = -1;
    private ArrayList<String> savedTitles = new ArrayList<>();

    private boolean isInternalUpdate = false;

    public void initialize() {
        searchTitle.setOnAction(this::onClickSearchTitle);
        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getWikiBookLink());
        backButton.setDisable(true);
        forwardButton.setDisable(true);
        navigationComboBox.setOnAction(this::onNavigationChanged);
    }

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

    public void onClickSortWikiBook(ActionEvent actionEvent) {
        String sortOrder = zettelkasten.getCurrentSortOrder();

        if (sortOrder == null || sortOrder.equals("ABSTEIGEND")){
            zettelkasten.sort("AUFSTEIGEND");
            updateMediaListView();
        }

        else if (sortOrder.equals("AUFSTEIGEND")){
            zettelkasten.sort("ABSTEIGEND");
            updateMediaListView();
        }


    }

    public void onClickDeleteWikiBook(ActionEvent actionEvent) {
        String selectedTitle = viewMedien.getSelectionModel().getSelectedItem();

        if (selectedTitle == null || selectedTitle.isEmpty()){
            warningEmptyTitle();
            return;
        }
        if (zettelkasten.dropMedium(selectedTitle, true, 0)) {
            updateMediaListView();
        }


    }

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

    public void onClickImport(ActionEvent actionEvent) {

        String errorText = "Die Importfunktion wird in dieser Version nicht unterstützt.";

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("FEHLER: Funktion nicht implementiert");
        alert.setHeaderText("Import fehlgeschlagen");
        alert.setContentText(errorText);
        alert.showAndWait();

        throw new UnsupportedOperationException(errorText);
    }

    public void onClickExport(ActionEvent actionEvent) {

        String errorText = "Die Exportfunktion wird in dieser Version nicht unterstützt.";

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("FEHLER: Funktion nicht implementiert");
        alert.setHeaderText("Export fehlgeschlagen");
        alert.setContentText(errorText);
        alert.showAndWait();

        throw new UnsupportedOperationException(errorText);
    }

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

    public void onSynonymListViewClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            String selectedTitle = viewSynonyme.getSelectionModel().getSelectedItem();

            if (selectedTitle != null && !selectedTitle.isEmpty()) {
                onClickSearchWikipedia(null);
            }
        }
    }

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

    private void startWikiBookFetchTask(String title) {
        lastUsernameValue.setText("Wird geladen...");
        lastChangeValue.setText("Wird geladen...");
        regalValue.setText("Wird geladen...");
        searchTitle.setDisable(true);

        Task<WikiBook> fetchTask = new Task<>() {
            @Override
            protected WikiBook call() throws Exception {
                WikiBook book = zettelkasten.fetchWikiBook(title);

                if (book == null) {
                    throw new Exception("Das WikiBook '" + title + "' konnte nicht abgerufen werden.");
                }
                return book;
            }
        };

        fetchTask.setOnSucceeded(event -> {
            WikiBook resultBook = fetchTask.getValue();
            updateUI(resultBook);
            searchTitle.setDisable(false);

            zettelkasten.addMedium(resultBook);
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

    private void startWikipediaFetchTask(String searchKeyword) {
        if (searchKeyword.trim().isEmpty()) {
            return;
        }

        viewSynonyme.setDisable(false);
        searchWikipediaButton.setDisable(false);

        Task<ArrayList<String>> fetchTask = new Task<>() {
            @Override
            protected ArrayList<String> call() throws Exception {
                return WikipediaAPI.fetchWikipediaTitles(searchKeyword);
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

    private void updateMediaListView() {
        viewMedien.getItems().clear();
        for (Medium medium : zettelkasten.getMyZettelkasten()) {
            viewMedien.getItems().add(medium.getTitel());
        }
    }

    private void updateCurrentZettelkastenMediaListView(String filterTitle) {
        viewMedien.getItems().clear();

        String normalizedFilter = filterTitle.toLowerCase();

        for (Medium medium : zettelkasten.getMyZettelkasten()) {
            if (medium.getTitel().toLowerCase().contains(normalizedFilter)) {
                viewMedien.getItems().add(medium.getTitel());
            }
        }
    }

    private void updateUI(WikiBook book) {
        if (book == null) {
            lastUsernameValue.setText("N/A");
            lastChangeValue.setText("N/A");
            regalValue.setText("N/A");
            return;
        }

        lastUsernameValue.setText(book.getDisplayContributor());

        lastChangeValue.setText(book.getFormattedLastModifiedDate());

        if (book.getRegale().isEmpty()) {
            regalValue.setText("N/A");
        } else {
            regalValue.setText(String.join(", ", book.getRegale()));
        }
    }

    private void updateNavigationButtons() {
        backButton.setDisable(currentIndex <= 0);
        forwardButton.setDisable(currentIndex >= savedTitles.size() - 1);
    }

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

    public void warningEmptyTitle(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNUNG");
        alert.setHeaderText("Leerer Titel");
        alert.setContentText("Du musst ein Titel angeben.");
        alert.showAndWait();
    }

    public void loadAndFetchEverything(String _title){
        String safeTitle = WikiBook.getURLTitle(_title);
        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getUrl(safeTitle));

        startWikiBookFetchTask(safeTitle);
        startWikipediaFetchTask(_title);

        updateCurrentZettelkastenMediaListView(_title);
    }

}
