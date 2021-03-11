public class Main {
    public static void main(String[] args) {
        try{
            DataLoader test = new DataLoader("data/patients100.csv");
        }catch(Exception e){
            System.out.println("error");
        }
    }
}
