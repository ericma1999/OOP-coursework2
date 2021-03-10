public class DataFrame {

    private int rowCount = 0;

    public void addColumn(String name, String row){
        return;
    }

    public void getColumnNames(){
        return;
    }

    public int getRowCount(){
        return this.rowCount;
    }

    public String getValue(String columnName, int row){
        return "Test";
    }

    public String putValue(String columnName, int row, String value){
        return "Put value";
    }

    public String addValue(String columnName, String value){
        return "Add value at the end of row";
    }

}
