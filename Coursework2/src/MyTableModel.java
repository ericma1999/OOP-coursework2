import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

class MyTableModel extends AbstractTableModel {
    private ArrayList<ArrayList<String>> data;
    private String[] columnNames;

    public MyTableModel(ArrayList<ArrayList<String>> data, String[] columnNames){
        this.data = data;
        this.columnNames = columnNames;
    }

    public int getColumnCount() {
        return this.columnNames.length;
    }

    public int getRowCount() {
        return this.data.size();
    }

    public String getColumnName(int col) {
        return this.columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }

    public void setData(ArrayList<ArrayList<String>> data){
        this.data = data;
        this.fireTableDataChanged();
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