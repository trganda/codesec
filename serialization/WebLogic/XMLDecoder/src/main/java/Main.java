import java.beans.XMLDecoder;
import java.lang.ProcessBuilder;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("poc.xml");
        XMLDecoder xd = null;
        try {
            xd = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object s2 = xd.readObject();
        xd.close();
    }
}