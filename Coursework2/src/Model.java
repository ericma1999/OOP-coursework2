import java.util.ArrayList;

public class Model {

    private DataFrame dataFrame;

    public Model (){
        try{
            this.dataFrame =  new DataLoader("data/patients100.csv").getDataFrame();
        }catch(Exception e){
            System.out.println("error");
        }
    }
    public int getTotalRows(){
        return this.dataFrame.getSize();
    }

    public String[] getColumnNames(){
        return this.dataFrame.getColumnNames();
    }

    public String getValueAt(int index, int row){
        return this.dataFrame.getColumn(index).getRowValue(row);
    }

    public ArrayList<ArrayList<String>> getAllData(){
        ArrayList<ArrayList<String>> output = new ArrayList<>();

        for (int i = 0; i < this.dataFrame.getSize(); i++) {
            output.add(this.dataFrame.getColumn(i).getRowValues());
        }
        return output;

    }

    public ArrayList<ArrayList<String>> findName(String name){
        ArrayList<ArrayList<String>> output = new ArrayList<>();
        for (int i = 0; i < getTotalRows(); i++) {
            Column currentColumn = dataFrame.getColumn(i);
            if (currentColumn.getName().toLowerCase().contains(name)){
                output.add(currentColumn.getRowValues());
            }
        }
        return output;
    }

}
