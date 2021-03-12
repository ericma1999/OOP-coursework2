import java.util.ArrayList;

public class DataFrame {

    private ArrayList<String> columnNames;
    private ArrayList<Column> columns;
    private int firstNameIndex = -1;
    private int lastNameIndex = -1;
    private int rowCount = 0;
    private int totalColumns = 0;

    public DataFrame(){
        this.columns = new ArrayList<>();
        this.columnNames = new ArrayList<>();
    }

    public void addColumn(String row){
        String[] rowSplit = row.split(",");
        Column newColumn = new Column(rowSplit[firstNameIndex] + " " + rowSplit[lastNameIndex], row);
        this.columns.add(newColumn);
        this.totalColumns += 1;
    }

    public Column getColumn(int index){
        return this.columns.get(index);
    }

    public int getTotalColumns(){
        return this.totalColumns;
    }

    public String[] getColumnNames(){
        return this.columnNames.toArray(new String[this.columnNames.size()]);
    }

    public void setColumnNames(String input){
        ArrayList<String> columnNames = new ArrayList<>();
        int rowCount = 0;
        for (String columnTitle: input.split(",")){
            if (columnTitle.equals("FIRST")){
                firstNameIndex = rowCount;
            }

            if (columnTitle.equals("LAST")){
                lastNameIndex = rowCount;
            }

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
