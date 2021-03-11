import java.util.ArrayList;

public class Column {
    private String name;
    private ArrayList<String> rows;
    private int size = 0;

    public Column(String name, String rows){
        this.name = name;
        this.loadRowContents(rows);
    }

    public String getName(){
        return this.name;
    }

    public int getSize(){
        return this.size;
    }

    private void loadRowContents(String rowContents){
        ArrayList<String> content = new ArrayList<>();
        int size = 0;
        for (String rowContent: rowContents.split(",")){
            content.add(rowContent);
            size += 1;
        }
        this.size = size;
        this.rows = content;
    }

    public String getRowValue(int index){
        return this.rows.get(index);
    }

    public void setRowValue(int index, String newValue){
        this.rows.set(index, newValue);
    }

    public void addRowValue(String newValue){
        this.rows.add(newValue);
        this.size += 1;
    }
}
