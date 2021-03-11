public class Main {
    public static void main(String[] args) {
//        try{
//            DataLoader test = new DataLoader("data/patients100.csv");
//        }catch(Exception e){
//            System.out.println("error");
//        }

        Column test = new Column("Eric Ma", "S89191,health fine");
        System.out.println(test.getName());
        System.out.println(test.getSize());
        System.out.println(test.getRowValue(0));
        test.addRowValue("hello");
        System.out.println(test.getSize());
        System.out.println(test.getRowValue(2));

    }
}
