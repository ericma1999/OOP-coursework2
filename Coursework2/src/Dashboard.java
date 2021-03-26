import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {

    public Dashboard(){
        this.render();
    }



    public void render(){
        JButton ageDistribution = new JButton("Show Age distribution");
        JButton livingDistribution = new JButton("Living distribution");

        ageDistribution.addActionListener(e -> {
            add(new JLabel("test"));
            revalidate();
            repaint();
        });

        add(ageDistribution);
        add(livingDistribution);
    }


}
