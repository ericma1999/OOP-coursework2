import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataFrame {

    private ArrayList<String> columnNames;
    private final ArrayList<Column> columns;
    private final LinkedHashMap<String, Column> columns2 = new LinkedHashMap<>();
    private int firstNameIndex = -1;
    private int lastNameIndex = -1;
    private int rowCount = 0;

    public DataFrame(){
        this.columns = new ArrayList<>();
        this.columnNames = new ArrayList<>();
    }

    public void addColumn(ArrayList<String> rowContent){

        Column newColumn = new Column(rowContent.get(firstNameIndex) + " " + rowContent.get(lastNameIndex), rowContent);
        this.columns.add(newColumn);
        this.rowCount += 1;
    }

    public Column getColumn(int index){
        return this.columns.get(index);
    }


    public String[] getColumnNames(){

        String[] columnNamesTest = new String[this.columns2.size()];
        int i = 0;
        for (Map.Entry<String, Column> entry : this.columns2.entrySet()) {
            columnNamesTest[i] = entry.getKey();
            i+=1;
        }


        return columnNamesTest;
    }

    public void setColumnNames(ArrayList<String> columnNames){
        this.columnNames = columnNames;
        this.firstNameIndex = columnNames.indexOf("FIRST");
        this.lastNameIndex = columnNames.indexOf("LAST");
        for (String name: columnNames) {
            columns2.put(name, null);
        }
    }

    public int getRowCount(){
        return this.rowCount;
    }

    private Column findColumn(String columnName){
        for (Column column: this.columns){
            if (column.getName().equals(columnName)){
                return column;
            }
        }
        return null;
    }

    public String getValue(String columnName, int row){

        Column selectedColumn = this.findColumn(columnName);
        if (selectedColumn == null) {
            return null;
        }
        return selectedColumn.getRowValue(row);
    }

    public boolean putValue(String columnName, int row, String value){
        Column selectedColumn = this.findColumn(columnName);
        if (selectedColumn == null) {
            return false;
        }
        selectedColumn.setRowValue(row, value);
        return true;
    }

    public boolean addValue(String columnName, String value){
        Column selectedColumn = this.findColumn(columnName);
        if (selectedColumn == null){
            return false;
        }
        selectedColumn.addRowValue(value);
        return true;
    }

}
