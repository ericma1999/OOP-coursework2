import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
                ArrayList<String> currentLineSplitted = new ArrayList<String>(Arrays.asList(currentLine.split(",")));
                if (i == 0){
                    this.dataFrame.setColumnNames(currentLineSplitted);
                    i+=1;
                    continue;
                }

                if (currentLineSplitted.size() < this.dataFrame.getColumnNames().length){
                    currentLineSplitted.add("");
                }
                this.dataFrame.addColumn(currentLineSplitted);
                i+=1;
            }

        } catch(Exception e){
            throw e;
        }
    }
}
