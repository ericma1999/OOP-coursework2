import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(final String[] args)
    {
//        SwingUtilities.invokeLater(GUI::new);

        Model model = new Model("./data/patients100.csv");

        HashMap<String, String> test = new HashMap<>();

        test.put("RACE", "asian");
        test.put("MARITAL", "m");


        ArrayList<ArrayList<String>> output = model.getDataWithFilters(test);


        for (ArrayList<String> result: output) {
            System.out.println(result);
        }

    }
}
