public class Model {

    private DataFrame dataFrame;

    public Model (){
        try{
            DataLoader test = new DataLoader("data/patients100.csv");
            this.dataFrame = test.getDataFrame();
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

}
