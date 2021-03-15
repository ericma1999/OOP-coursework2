import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(GUI::new);
    }
}
