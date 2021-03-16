import java.util.ArrayList;

public class Column {
    private final String name;
    private final ArrayList<String> rows;
    private int size = 0;

    public Column(String name){
        this.name = name;
        this.rows = new ArrayList<>();
    }

    public Column(String name, ArrayList<String> rowContent){
        this.name = name;
        this.rows = rowContent;
    }

    public String getName(){
        return this.name;
    }

    public int getSize(){
        return this.size;
    }

    public String getRowValue(int index){
        return this.rows.get(index);
    }

    public ArrayList<String> getRowValues() {return new ArrayList<>(this.rows);}

    public void setRowValue(int index, String newValue){
        this.rows.set(index, newValue);
    }

    public void addRowValue(String newValue){
        this.rows.add(newValue);
        this.size += 1;
    }
}
