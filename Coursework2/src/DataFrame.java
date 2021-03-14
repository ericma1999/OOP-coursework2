import java.util.ArrayList;

public class DataFrame {

    private ArrayList<String> columnNames;
    private final ArrayList<Column> columns;
    private int firstNameIndex = -1;
    private int lastNameIndex = -1;
    private int rowCount = 0;
    private int size = 0;

    public DataFrame(){
        this.columns = new ArrayList<>();
        this.columnNames = new ArrayList<>();
    }

    public void addColumn(ArrayList<String> rowContent){

        Column newColumn = new Column(rowContent.get(firstNameIndex) + " " + rowContent.get(lastNameIndex), rowContent);
        this.columns.add(newColumn);
        this.size += 1;
    }

    public Column getColumn(int index){
        return this.columns.get(index);
    }

    public int getSize(){
        return this.size;
    }

    public String[] getColumnNames(){
        return this.columnNames.toArray(new String[0]);
    }

    public void setColumnNames(ArrayList<String> columnNames){
        this.columnNames = columnNames;
        this.firstNameIndex = columnNames.indexOf("FIRST");
        this.lastNameIndex = columnNames.indexOf("LAST");
        this.rowCount = columnNames.size();
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
