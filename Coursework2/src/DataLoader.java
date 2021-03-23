import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DataLoader {

    private DataFrame dataFrame = new DataFrame();
    private static final ArrayList<String> supportedTypes = new ArrayList<>(Arrays.asList("json", "csv"));

    public DataLoader(String filePath) throws IOException {
        this.loadContent(filePath);
    }

    public DataFrame getDataFrame() {
        return this.dataFrame;
    }


    private String getFileExtension(String path) {
//        get the position of last . until end of string
        return path.substring(path.lastIndexOf(".") + 1);

    }

    public void loadContent(String filePath) throws IOException {

        String extension = this.getFileExtension(filePath);

        if (!supportedTypes.contains(extension)) {
            throw new IOException("File format is incorrect");
        }

        if (extension.equals("json")) {
            this.readJSON(filePath);
        } else {
            FileReader file = new FileReader(filePath);
            this.readCSV(file);
        }

    }

    private void readJSON(String filePath) throws IOException {
        JSONReader reader = new JSONReader(filePath);

        this.dataFrame = reader.getDataFrame();
    }


    private void readCSV(FileReader file) throws IOException {
        /*
         * try with resources will perform all the cleanup, but since we want to handle
         * exception at application level allow the exception to propagate up
         */

        try (BufferedReader contents = new BufferedReader(file)) {
            String currentLine;
            boolean first = true;
            int noOfColumns = -1;
            while ((currentLine = contents.readLine()) != null) {
                ArrayList<String> currentLineSplitted = new ArrayList<>(Arrays.asList(currentLine.split(",")));
                if (first) {
                    this.dataFrame.setColumnNames(currentLineSplitted);
                    first = false;
                    noOfColumns = this.dataFrame.getColumnNames().size();
                    continue;
                }

                if (currentLineSplitted.size() < noOfColumns) {
                    currentLineSplitted.add("");
                }

                this.dataFrame.addRow(currentLineSplitted);
            }
        }

    }
}
