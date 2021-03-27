import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Model {

    private final DataFrame dataFrame;

    public Model (String filePath) throws IOException {
        this.dataFrame =  new DataLoader(filePath).getDataFrame();
    }

    public int getTotalRows(){
        return this.dataFrame.getRowCount();
    }

    public List<String> getColumnNames(){
        return this.dataFrame.getColumnNames();
    }

    public String getValueAt(int index, int row){
        return this.dataFrame.getColumn(index).getRowValue(row);
    }

    public String getValueAt(String columnName, int row){ return this.dataFrame.getColumn(columnName).getRowValue(row);}

    public List<List<String>> getAllData(){
        List<List<String>> output = new ArrayList<>();

        for (int i = 0; i < this.dataFrame.getRowCount(); i++) {
            output.add(this.dataFrame.getRow(i));
        }
        return output;

    }
    public List<List<String>> getDataWithFilters(Map<String, String> filters){
        List<List<String>> output = new ArrayList<>();

        for (int i = 0; i < this.dataFrame.getRowCount(); i++) {
            boolean shouldAdd = true;
//            loop through the hashmap keys and check the value, if the column matches all the hashmap's key value
//            add it to the output
            for (String columnName: filters.keySet()) {

                String currentColumnRowValue = this.dataFrame.getColumn(columnName).getRowValue(i).toLowerCase();

                if (!currentColumnRowValue.contains(filters.get(columnName).toLowerCase())) {
                    shouldAdd = false;
                }
            }
            if (shouldAdd){
                output.add(this.dataFrame.getRow(i));
            }
        }
        return output;
    }


    public void writeToJSON(String path){
        new JSONWriter(this.dataFrame, path);
    }

    public List<String> getRow(int index){
        return dataFrame.getRow(index);
    }
}
