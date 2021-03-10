import java.util.ArrayList;

public class Column {
    private String name;
    private String rows;

    public Column(String name, String rows){
        this.name = name;
        this.rows = rows;
    }

    public String getName(){
        return this.name;
    }

    public int getSize(){
        return 0;
    }

    public String getRowValue(int index){
        return "Test";
    }

    public void setRowValue(String newValue){
        // set new value later
        return;
    }

    public void addRowValue(String newValue){
        return;
    }



}
