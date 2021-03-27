import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {
    public JSONWriter(DataFrame dataFrame, String pathName) throws IOException{

        try(BufferedWriter writer =
                    new BufferedWriter(new FileWriter(pathName))){
            writer.write(generateJSONString(dataFrame).toString());
        }
    }

    private StringBuilder generateJSONString(DataFrame dataFrame) {
        StringBuilder output = new StringBuilder();

        output.append("[");

        int totalRows = dataFrame.getRowCount();
        int totalColumns = dataFrame.getColumnNames().size();
        for (int i = 0; i < totalRows; i++) {
            output.append("{");
            int counter = 0;
            for (String propertyName : dataFrame.getColumnNames()) {

                String singleProperty = String.format("\"%s\": \"%s\"", propertyName, dataFrame.getColumn(propertyName).getRowValue(i));
                output.append(singleProperty);
                if (totalColumns - 1 != counter){
                    output.append(",");
                }
                counter++;
            }
            output.append("}");

            if (totalRows - 1 != i) {
                output.append(",");
            }
        }

        output.append("]");
        return output;
    }
}
