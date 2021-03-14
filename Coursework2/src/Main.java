import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(final String[] args)
    {
//        SwingUtilities.invokeLater(GUI::new);
            Model model = new Model("./data/patients100.csv");

        ArrayList<ArrayList<String>> oldestPersons = model.findOldest();

        System.out.println(oldestPersons);




//        String testDate = "1979-08-24";
//        try{
//            Date testNewDate = new SimpleDateFormat("yyyy-MM-dd").parse(testDate);
//            Date today = new Date();
//
//            long milliSecondsAlive = today.getTime() - testNewDate.getTime();
//            long diff = TimeUnit.MINUTES.convert(milliSecondsAlive, TimeUnit.MILLISECONDS);
//
//            System.out.println(diff);
//
//        }catch (Exception e){
//            System.out.println("somethign went wrong");
//        }

    }
}
