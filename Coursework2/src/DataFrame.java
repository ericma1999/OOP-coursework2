
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataFrame {

    private final LinkedHashMap<String, Column> columns;
    private final ArrayList<String> keys = new ArrayList<>();
    private int rowCount = 0;

    public DataFrame(){
        this.columns = new LinkedHashMap<>();
    }

    public void addRow(ArrayList<String> rowContent){
        int i = 0;
        for (String columnValue: rowContent) {
            System.out.println(this.columns.get(keys.get(i)));
            this.columns.get(keys.get(i)).addRowValue(columnValue);
            i++;
        }
        this.rowCount += 1;
    }

    public Column getColumn(int index){
        return this.columns.get(keys.get(index));
    }

    public String[] getColumnNames(){

        String[] columnNamesTest = new String[this.columns.size()];
        int i = 0;
        for (Map.Entry<String, Column> entry : this.columns.entrySet()) {
            columnNamesTest[i] = entry.getKey();
            i+=1;
        }
        return columnNamesTest;
    }

    public void setColumnNames(ArrayList<String> columnNames){
        for (String name: columnNames) {
            columns.put(name, new Column(name));
            keys.add(name);
        }
    }

    public int getRowCount(){
        return this.rowCount;
    }

//    private Column findColumn(String columnName){
//        for (Column column: this.columns){
//            if (column.getName().equals(columnName)){
//                return column;
//            }
//        }
//        return null;
//    }

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
