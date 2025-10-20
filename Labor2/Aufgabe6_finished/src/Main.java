import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main{
    public static void main(String[] args) throws IOException{

        String fileName = "test2.txt";

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
        writer.write("Die Welt kostet 17 €");
        writer.newLine(); // 13 10
        writer.close();
        InputStream in = new FileInputStream(fileName);
        int b;
        while ((b = in.read()) != -1){
            System.out.print(b + " ");
        }
        in.close();
    }

}
