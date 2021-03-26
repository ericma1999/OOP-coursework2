package uk.ac.ucl.passawis.charts;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class PieChart extends Chart{
    private double total;


    @Override
    public void initialise(String chartTitle, Map<String, Double> data) {
        super.initialise(chartTitle, data);
        calculateTotal();
    }

    private void calculateTotal(){
        double tempTotal = 0;

        for(double value: this.data.values()){
            tempTotal += value;
        }

        this.total = tempTotal;
    }

    private double calculatePercentage(double value){
        return value / total * 100;
    }

    /* calculate turn angle and round it to get an integer value */
    private int calculateTurnAngle(double value){
        return (int) Math.round(((value / total) * 360));
    }

    private void renderChart(Graphics g){
        int startAngle = 0;
        this.legends = new LinkedHashMap<>();

        boolean noColours = colors.isEmpty();

        int colorPicker = 0;
        for (Map.Entry<String, Double> result:this.data.entrySet()){
            int turnAngle = calculateTurnAngle(result.getValue());
            if (noColours){
                g.setColor(randomColourPicker());
            }else {
                colorPicker = this.colors.size() == colorPicker ? 0 : colorPicker;
                g.setColor(this.colors.get(colorPicker));
                colorPicker+= 1;
            }
            legends.put(String.format("%s %.2f%%", result.getKey(), calculatePercentage(result.getValue())), g.getColor());
            g.fillArc(startingPoint.getX(), startingPoint.getY(), 500, 500, startAngle, turnAngle);
            startAngle += turnAngle;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawChartTitle(g);
        renderChart(g);
        Legend legend = new Legend(legends);
        legend.setXYStartingPoint(200, 700);
        add(legend);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 720);
    }

}

