import java.util.ArrayList;

public class Column {
    private String name;
    private ArrayList<String> rows;
    private int size = 0;

    public Column(String name, ArrayList<String> rowContent){
        this.name = name;
        this.rows = rowContent;
    }

    public String getName(){
        return this.name;
    }

    public int getSize(){
        return this.rows.size();
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
