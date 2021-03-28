
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataFrame {

    private final LinkedHashMap<String, Column> columns;
    private final ArrayList<String> keys = new ArrayList<>();
    private int rowCount = 0;

    public DataFrame(){
        this.columns = new LinkedHashMap<>();
    }

    public boolean addRow(List<String> rowContent){
        /* If the amount of columns not the same as specified columns initially, not valid */
        if (rowContent.size() != keys.size()){
            return false;
        }
        int i = 0;
        for (String columnValue: rowContent) {
            this.columns.get(keys.get(i)).addRowValue(columnValue);
            i++;
        }
        this.rowCount += 1;
        return true;
    }

    public List<String> getRow(int index){
        ArrayList<String> output = new ArrayList<>();

        for (String key: keys) {
            output.add(this.columns.get(key).getRowValue(index));
        }
        return output;

    }

    public Column getColumn(int index){
        return this.columns.get(keys.get(index));
    }

    public Column getColumn(String columnName){
        return this.columns.get(columnName);
    }

    public List<String> getColumnNames(){
        return new ArrayList<>(this.keys);
    }

    public void setColumnNames(List<String> columnNames){
        for (String name: columnNames) {
//            columns.put(name, new Column(name));
//            keys.add(name);
            addColumn(name);
        }
    }

    public void addColumn(String columnName){
        if (columns.get(columnName) != null){
            return;
        }

        columns.put(columnName, new Column(columnName));
        keys.add(columnName);
    }

    public int getRowCount(){
        return this.rowCount;
    }

    public String getValue(String columnName, int row){

        Column selectedColumn = this.columns.get(columnName);
        if (selectedColumn == null) {
            return null;
        }
        return selectedColumn.getRowValue(row);
    }

    public boolean putValue(String columnName, int row, String value){
        Column selectedColumn = this.columns.get(columnName);
        if (selectedColumn == null) {
            return false;
        }
        selectedColumn.setRowValue(row, value);
        return true;
    }

    public boolean addValue(String columnName, String value){
        Column selectedColumn = this.columns.get(columnName);
        if (selectedColumn == null){
            return false;
        }
        selectedColumn.addRowValue(value);
        return true;
    }

}
