import java.util.ArrayList;

public class DataFrame {

    private ArrayList<String> columnNames;
    private ArrayList<Column> columns = new ArrayList<>();
    private int rowCount = 0;


    public void addColumn(String name, String row){
        Column newColumn = new Column(name, row);
        this.columns.add(newColumn);
    }

    public String[] getColumnNames(){
        return this.columnNames.toArray(new String[this.columnNames.size()]);
    }

    public void setColumnNames(String input){
        ArrayList<String> columnNames = new ArrayList<>();
        int rowCount = 0;
        for (String columnTitle: input.split(",")){
            columnNames.add(columnTitle);
            rowCount += 1;
        }
        this.rowCount = rowCount;
        this.columnNames = columnNames;
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
