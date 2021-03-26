import uk.ac.ucl.passawis.charts.BarChart;
import uk.ac.ucl.passawis.charts.PieChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Dashboard extends JPanel {
    ArrayList<Color> colours = new ArrayList<>();
    LinkedHashMap<String, Double> results = new LinkedHashMap<>();

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

        results.put("0-10", 200.0);
        results.put("11-20", 300.0);
        results.put("21-30", 80.0);
        results.put("31-40", 250.0);
        results.put("41-50", 250.0);
        results.put("51-60", 10000.0);

        this.render();
    }


    private void renderPieChart(){
        removeAll();
        PieChart pieChart = new PieChart();
        pieChart.initialise("Test", results);
        pieChart.setTitle("Share", 700, 50);
        pieChart.setXYStartingPoint(600, 100);
        add(pieChart, SwingConstants.CENTER);
        revalidate();
        repaint();
    }

    private void renderBarChart(){
        removeAll();
        BarChart barChart = new BarChart();
        barChart.initialise("Test", results);
        barChart.setTitle("Share", 700, 50);
        barChart.setXYStartingPoint(400, 500);
        barChart.showBarLabel(false);
        barChart.setLegendXYPosition(400, 500);
        add(barChart);
        revalidate();
        repaint();
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