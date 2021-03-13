import javax.swing.*;

public class Main {

    public static void main(final String[] args)
    {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new GUI();
//            }
//        });
//
        Model model = new Model();
        String[][] test = model.getAllData();
        System.out.println(test[0][1]);
//        String[] column = model.findName("Tori");

//        System.out.println(column[0]);
//        String[] test = new String[]{"hello", "eric"};
//        String[] test2 = test.clone();
//        System.out.println(test2[0]);
//        test[0] = "Test";
//        System.out.println(test2[0]);



    }
}
