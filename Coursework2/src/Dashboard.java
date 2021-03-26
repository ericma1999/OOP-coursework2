import uk.ac.ucl.passawis.charts.BarChart;
import uk.ac.ucl.passawis.charts.PieChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Dashboard extends JPanel {
    ArrayList<Color> colours = new ArrayList<>();
    LinkedHashMap<String, Double> results = new LinkedHashMap<>();

    private Supplier<Map<String, Double>> handleOccurencesClick;

    public Dashboard(){
        colours.add(new Color(0, 168, 255));
        colours.add(new Color(156, 136, 255));
        colours.add(new Color(251, 197, 49));
        colours.add(new Color(76, 209, 55));
        colours.add(new Color(72, 126, 176));
        colours.add(new Color(232, 65, 24));
        colours.add(new Color(220, 221, 225));
        colours.add(new Color(127, 143, 166));
        colours.add(new Color(39, 60, 117));
        colours.add(new Color(53, 59, 72));
        colours.add(new Color(25, 42, 86));

        results.put("asian", 200.0);
        results.put("white", 300.0);
        results.put("french", 80.0);

        this.render();
    }


    private void renderPieChart(){
        removeAll();
        PieChart pieChart = new PieChart();
        pieChart.initialise("Test", handleOccurencesClick.get());
        pieChart.setTitle("Share", 700, 50);
        pieChart.setXYStartingPoint(600, 100);
        add(pieChart);
        revalidate();
        repaint();
    }

    private void renderBarChart(){
        removeAll();
        BarChart barChart = new BarChart();
        barChart.initialise("Test", handleOccurencesClick.get());
        barChart.setTitle("Share", 700, 50);
        barChart.setXYStartingPoint(200, 500);
        barChart.showBarLabel(false);
        barChart.setLegendXYPosition(200, 500);
        add(barChart);
        revalidate();
        repaint();
    }

    public void onHandleLivingClick(Supplier<Map<String, Double>> callback){
        this.handleOccurencesClick = callback;
    }

    public void render(){
        JButton ageDistribution = new JButton("Show Age distribution");
        JButton livingDistribution = new JButton("Living distribution");

        ageDistribution.addActionListener(e -> renderPieChart());

        livingDistribution.addActionListener(e -> renderBarChart());

        add(ageDistribution);
        add(livingDistribution);
    }

}
