package uk.ac.ucl.passawis.ui;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.function.Consumer;

public class MyTable extends JTable {

    HashMap<String, Pair<Integer, TableColumn>> removedColumns = new HashMap<>();

    public MyTable(AbstractTableModel model){
        super(model);
        this.setRowHeight(30);
    }


    public void unhideColumn(String columnName){
        Pair<Integer, TableColumn> result = removedColumns.get(columnName);
        if (result == null){
            return;
        }
        this.addColumn(result.getValue1());
        int targetColumn = result.getValue0();

        if (targetColumn > this.getColumnCount()){
            targetColumn = this.getColumnCount() - 1;
        }
        this.moveColumn(this.getColumnCount() - 1, targetColumn);

        removedColumns.remove(columnName);
    }

    public void setHeaderClickAction(Consumer<MouseEvent> callback){
        this.tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                callback.accept(e);
            }
        });
    }

    public void hideColumn(String columnName){
        TableColumn removedColumn = this.getColumn(columnName);
        super.removeColumn(removedColumn);
        removedColumns.put(columnName, new Pair<>(removedColumn.getModelIndex(), removedColumn));
    }



}
