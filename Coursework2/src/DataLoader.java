import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DataLoader {

    private final DataFrame dataFrame = new DataFrame();

    public DataLoader(String filePath) throws IOException {
        this.loadContent(filePath);
    }

    public DataFrame getDataFrame() {
        return this.dataFrame;
    }

    public void loadContent(String filePath) throws IOException {
        FileReader file = new FileReader(filePath);

        try (BufferedReader contents = new BufferedReader(file)) {

            String currentLine;
            boolean first = true;
            int noOfColumns = -1;
            while ((currentLine = contents.readLine()) != null) {
                ArrayList<String> currentLineSplitted = new ArrayList<>(Arrays.asList(currentLine.split(",")));
                if (first) {
                    this.dataFrame.setColumnNames(currentLineSplitted);
                    first = false;
                    noOfColumns = this.dataFrame.getColumnNames().length;
                    continue;
                }

                if (currentLineSplitted.size() < noOfColumns) {
                    currentLineSplitted.add("");
                }
                this.dataFrame.addRow(currentLineSplitted);
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
