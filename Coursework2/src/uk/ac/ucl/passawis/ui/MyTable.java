package uk.ac.ucl.passawis.ui;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public class MyTable extends JTable {

    HashMap<String, Pair<Integer, TableColumn>> removedColumns = new HashMap<>();

    public MyTable(AbstractTableModel model){
        super(model);
        this.setRowHeight(30);
    }


    public void unhideColumn(String columnName){
        /* Stores the model index and the content of the removed column */
        Pair<Integer, TableColumn> result = removedColumns.get(columnName);
        if (result == null){
            return;
        }
        this.addColumn(result.getValue1());

        this.moveColumn(this.getColumnCount() - 1, calculateColumnPosition(result.getValue0()));

        removedColumns.remove(columnName);
    }

    /* Loop through and compare model index to determine which position */
    /* Should we show the column on when unhide */
    private int calculateColumnPosition(int removedModelIndex){
        Iterator<TableColumn> currentColumns = this.columnModel.getColumns().asIterator();
        int pos = 0;
        while (currentColumns.hasNext()){
            if (currentColumns.next().getModelIndex() < removedModelIndex){
                pos +=1;
                continue;
            }
            break;
        }
        return pos;
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
