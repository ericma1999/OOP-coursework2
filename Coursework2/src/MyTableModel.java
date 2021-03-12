import javax.swing.table.AbstractTableModel;

class MyTableModel extends AbstractTableModel {
    private Model model;

    public MyTableModel(Model model){
        this.model = model;
    }

    public int getColumnCount() {
        int columnCount = model.getColumnNames().length;
        return columnCount;
    }

    public int getRowCount() {
        return model.getTotalRows();
    }

    public String getColumnName(int col) {
        return model.getColumnNames()[col];
    }

    public Object getValueAt(int row, int col) {
        return model.getValueAt(row, col);
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