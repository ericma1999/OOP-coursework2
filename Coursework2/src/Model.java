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

    public String[][] getAllData(){
        String[][] output = new String[this.dataFrame.getSize()][this.dataFrame.getRowCount()];

        for (int i = 0; i < this.dataFrame.getSize(); i++) {
            output[i] = this.dataFrame.getColumn(i).getRowValues();
        }
        return output;

    }

    public String[] findName(String name){
        for (int i = 0; i < getTotalRows(); i++) {
            if (dataFrame.getColumn(i).getName().contains(name)){
                return dataFrame.getColumn(i).getRowValues();
            }
        }
        return null;
    }

}
