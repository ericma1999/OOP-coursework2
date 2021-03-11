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

    public void test(){
        for (String columnName: dataFrame.getColumnNames()){
            System.out.println(columnName);
        }
    }

    public void testRow(){
        for (int i = 0; i < this.dataFrame.getTotalColumns(); i++) {
            System.out.println(this.dataFrame.getColumn(i).getName());
        }
    }
}
