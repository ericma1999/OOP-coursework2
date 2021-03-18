import javax.swing.SwingUtilities;


public class Main {

    public static void main(final String[] args) {
//        SwingUtilities.invokeLater(GUI::new);
//        DataFrame test = null;
//        try {
//            test = new DataLoader("./data/patients100.csv").getDataFrame();
//        } catch (Exception e) {
//            System.out.println("error");
//        }
//
//        if(test != null){
//            new JSONWriter(test);
//        }

        try{
            new JSONReader("./json/test.json");
        }catch (Exception e){
            System.out.println("error");
        }

    }
}
