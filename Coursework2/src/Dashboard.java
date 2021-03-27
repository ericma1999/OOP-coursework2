import uk.ac.ucl.passawis.charts.BarChart;
import uk.ac.ucl.passawis.charts.PieChart;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

public class Dashboard extends JPanel {

    private Function<String, Map<String, Double>> handleOccurrencesClick;

    public Dashboard(){
        this.render();
    }


    private void renderPieChart(String columnName){
        Map<String, Double> results = handleOccurrencesClick.apply(columnName);
        if (!results.isEmpty()) {
            removeAll();
            PieChart pieChart = new PieChart();
            pieChart.initialise("", handleOccurrencesClick.apply(columnName));
            pieChart.setTitle(String.format("%s distribution", columnName.toLowerCase()), 200, 50);
            pieChart.setXYStartingPoint(300, 100);
            pieChart.setLegendXYPosition(100, 400);
            add(pieChart);
            revalidate();
            repaint();
        }
    }

    private int calculateAxisMultiplier(Map<String, Double>results){
        double max = Collections.max(results.values());
        return (int) Math.ceil(max / 500);
    }

    private void renderBarChart(String columnName){
        Map<String, Double> results = handleOccurrencesClick.apply(columnName);
        if (!results.isEmpty()){
            removeAll();
            BarChart barChart = new BarChart();
            barChart.initialise("", results);
            barChart.setTitle(String.format("%s Barchart", columnName.toLowerCase()), 500, 50);
            barChart.setXYStartingPoint(200, 600);
            barChart.showBarLabel(false);
            barChart.setAxisMultiplier(calculateAxisMultiplier(results));
            barChart.setColumnWidth(50);
            barChart.setLegendXYPosition(200, 700);
            add(barChart);
            revalidate();
            repaint();
        }
    }

    public void onHandleLivingClick(Function<String,Map<String, Double>> callback){
        this.handleOccurrencesClick = callback;
    }

    public void render(){
        this.setLayout(new GridLayout(4,1));
        JPanel genderPanel = new JPanel(new GridLayout());
        JPanel ethnicPanel = new JPanel(new GridLayout());
        JPanel racePanel = new JPanel(new GridLayout());
        JPanel maritalPanel = new JPanel(new GridLayout());

        JButton genderPercentageDistribution = new JButton("Gender Percentage Distribution");
        JButton genderBarChart = new JButton("Gender Barchart");

        JButton ethnicPercentageDistribution = new JButton("Ethnic Percentage Distribution");
        JButton ethnicBarChart = new JButton("Ethnic Barchart");

        JButton racePercentageDistribution = new JButton("Race Percentage Distribution");
        JButton raceBarChart = new JButton("Race Barchart");


        JButton maritalPercentageDistribution = new JButton("Marital Percentage Distribution");
        JButton maritalBarChart = new JButton("Marital Barchart");

        genderPanel.add(genderBarChart);
        genderPanel.add(genderPercentageDistribution);
        ethnicPanel.add(ethnicBarChart);
        ethnicPanel.add(ethnicPercentageDistribution);
        racePanel.add(racePercentageDistribution);
        racePanel.add(raceBarChart);
        maritalPanel.add(maritalPercentageDistribution);
        maritalPanel.add(maritalBarChart);


        genderPercentageDistribution.addActionListener(e -> renderPieChart("GENDER"));
        genderBarChart.addActionListener(e -> renderBarChart("GENDER"));

        ethnicPercentageDistribution.addActionListener(e -> renderPieChart("ETHNICITY"));
        ethnicBarChart.addActionListener(e -> renderBarChart("ETHNICITY"));

        racePercentageDistribution.addActionListener(e -> renderPieChart("RACE"));
        raceBarChart.addActionListener(e -> renderBarChart("RACE"));

        maritalPercentageDistribution.addActionListener(e -> renderPieChart("MARITAL"));
        maritalBarChart.addActionListener(e -> renderBarChart("MARITAL"));



        add(genderPanel);
        add(ethnicPanel);
        add(racePanel);
        add(maritalPanel);

    }

}
