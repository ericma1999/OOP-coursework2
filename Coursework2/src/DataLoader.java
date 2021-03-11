import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataLoader {

    private DataFrame dataFrame = new DataFrame();

    public DataLoader(String filePath) throws IOException {
        this.loadContent(filePath);
    }

    public DataFrame getDataFrame(){
        return this.dataFrame;
    }

    public void loadContent(String filePath) throws IOException {
        try {
            FileReader file = new FileReader(filePath);
            BufferedReader contents = new BufferedReader(file);
            String currentLine;
            int i = 0;
            while ((currentLine = contents.readLine()) != null){
                if (i == 0){
                    this.dataFrame.setColumnNames(currentLine);
                    i+=1;
                    continue;
                }

                this.dataFrame.addColumn(currentLine);
                i+=1;
            }

        } catch(Exception e){
            throw e;
        }
    }
}
