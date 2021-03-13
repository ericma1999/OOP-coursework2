import javax.swing.table.AbstractTableModel;

class MyTableModel extends AbstractTableModel {
    private String[][] data;
    private String[] columnNames;

    public MyTableModel(String[][] data, String[] columnNames){
        this.data = data;
        this.columnNames = columnNames;
    }

    public int getColumnCount() {
        return this.columnNames.length;
    }

    public int getRowCount() {
        return this.data.length;
    }

    public String getColumnName(int col) {
        return this.columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public void setData(String[][] data){
        this.data = data;
    }

    /*
     * rather than a check box.
     */
//    public Class getColumnClass(int c) {
//        return getValueAt(0, c).getClass();
//    }
//
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
//    public void setValueAt(Object value, int row, int col) {
//
//        data[row][col] = value;
//        fireTableCellUpdated(row, col);
//
//    }
}