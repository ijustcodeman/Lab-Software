import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class B5 {
    public static void main(String[] args) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("test.txt"));
            out.write("Lorem ipsum dolor sit amet");
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
