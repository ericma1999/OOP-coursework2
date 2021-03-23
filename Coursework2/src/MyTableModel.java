import javax.swing.table.AbstractTableModel;
import java.util.List;

class MyTableModel extends AbstractTableModel {
    private List<List<String>> data;
    private final List<String> columnNames;

    public MyTableModel(List<List<String>> data, List<String> columnNames){
        this.data = data;
        this.columnNames = columnNames;
    }

    public int getColumnCount() {
        return this.columnNames.size();
    }

    public int getRowCount() {
        return this.data.size();
    }

    @Override
    public String getColumnName(int col) {
        return this.columnNames.get(col);
    }

    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }

    public void setData(List<List<String>> data){
        this.data = data;
        this.fireTableDataChanged();
    }
}