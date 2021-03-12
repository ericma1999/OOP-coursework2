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

}
