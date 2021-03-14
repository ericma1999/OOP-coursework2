import java.util.ArrayList;

public class Model {

    private DataFrame dataFrame;

    public Model (String fileName){
        try{
            this.dataFrame =  new DataLoader(fileName).getDataFrame();
        }catch(Exception e){
            System.out.println("error");
        }
    }
    public int getTotalRows(){
        return this.dataFrame.getRowCount();
    }

    public String[] getColumnNames(){
        return this.dataFrame.getColumnNames();
    }

    public String getValueAt(int index, int row){
        return this.dataFrame.getColumn(index).getRowValue(row);
    }

    public ArrayList<ArrayList<String>> getAllData(){
        ArrayList<ArrayList<String>> output = new ArrayList<>();

        for (int i = 0; i < this.dataFrame.getRowCount(); i++) {
            output.add(this.dataFrame.getRow(i));
        }
        return output;

    }

    public ArrayList<ArrayList<String>> findName(String name){
        ArrayList<ArrayList<String>> output = new ArrayList<>();

        Column firstNameCol = this.dataFrame.getColumn("FIRST");
        Column lastNameCol = this.dataFrame.getColumn("LAST");
        for (int i = 0; i < dataFrame.getRowCount(); i++) {
            String nameTest = firstNameCol.getRowValue(i).concat(" ").concat(lastNameCol.getRowValue(i));
            if (nameTest.toLowerCase().contains(name)){
                output.add(this.dataFrame.getRow(i));
            }
        }
        return output;
    }

}
