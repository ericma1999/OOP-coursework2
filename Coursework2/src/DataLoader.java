import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {
    public DataLoader(String filePath) throws IOException {
        this.loadContent(filePath);
    }

    public void loadContent(String filePath) throws IOException {
        try {
            FileReader file = new FileReader(filePath);
            BufferedReader contents = new BufferedReader(file);
            String currentLine;
            while ((currentLine = contents.readLine()) != null){
                System.out.println(currentLine);
            }

        } catch(Exception e){
            throw e;
        }
    }
}
