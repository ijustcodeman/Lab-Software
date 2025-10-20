import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DWS {

  /**
   * @author ...
   *
   *
   * fÜR kOMMENTARE UND sTYLEGUIDES BLIEB KEINE zEIT
   */
      public void main(String[] args) throws IOException {

      /*
      try {
        BufferedReader in
            = new BufferedReader(new FileReader("DWS-TOP-DIVIDENDE.csv"));

        // die ersten sechs Zeilen überlesen
        int cnt = 0;
        while (in.readLine() != null && cnt < 7) {
          in.readLine();
          System.out.println(in);
          cnt++;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String s;
        Date je = null;
        try {
          je = sdf.parse("30.12.2011");
          System.out.printf("Jahresende gesetzt: %tF%n", je);
        } catch (ParseException e) {
          e.printStackTrace();
        }

        while ((s = in.readLine()) != null) {
          int i = s.indexOf(';');
          String ds = s.substring(0, i); //irgendwie umständlich, geht das nicht komfortabler?

          Date d = null;
          try {
            d =sdf.parse(ds);
          } catch (ParseException e) {
            e.printStackTrace();
            break;
          }
          if (je.equals(d)) {
            System.out.printf("gefunden %tF%n", d);
            for (String x : s.split(";")) {
              System.out.println(x);
            }

          }
        }

      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

       */

          String csvName = "DWS-TOP-DIVIDENDE.csv";
          ArrayList<String[]> records = new ArrayList<>();


          // csv einlesen
          try (BufferedReader in = new BufferedReader(new FileReader(csvName))){
              String line;
              while ((line = in.readLine()) != null){
                  String[] values = line.split(";");
                  records.add(values);
              }
              if (records.isEmpty()) {
                  System.err.println("CSV-Datei ist leer.");
                  return;
              }

              // Spalte und Zeile von Schlusskurs bekommen
              String suchendeSpalte = "Schlusskurs";
              int spalte = -1;
              int zeile = 0;

              for (int i = 0; i < records.size(); i++) {
                  String[] row = records.get(i);
                  for (int j = 0; j < row.length; j++) {
                      if (row[j].equalsIgnoreCase(suchendeSpalte)) {
                          spalte = j;
                          zeile = i;
                          break;
                      }
                  }
              }

              String[] ersterKursArr = records.get(zeile + 1);
              String ersterKurs = ersterKursArr[spalte].replace(',', '.');

              String[] letzterKursArr = records.get(records.size() - 1);
              String letzterKurs = letzterKursArr[spalte].replace(',', '.');

              System.out.println("Erster Kurs: " + ersterKurs);
              System.out.println("Letzter Kurs: " + letzterKurs);

              if (Double.parseDouble(ersterKurs) > Double.parseDouble(letzterKurs)){
                  System.out.println("bear");
              }
              else{
                  System.out.println("bull");
              }
          } catch (Exception e){
              System.err.println("Fehler: " + e.getMessage());
          }


      }
  }
