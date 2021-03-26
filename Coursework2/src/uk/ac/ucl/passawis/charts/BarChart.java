package uk.ac.ucl.passawis.charts;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class BarChart extends Chart{
    private int columnWidth = 80;
    private int finalWidth = 0;
    private int defaultSpacing = 50;
    private int axisMultiplier = 1;
    private boolean showBarLabel = true;

    @Override
    public void initialise(String chartTitle, Map<String, Double> data){
        super.initialise(chartTitle, data);
        this.finalWidth = data.size() * (this.columnWidth + 50);
    }

    public void setColumnWidth(int width){
        this.columnWidth = width;
        this.finalWidth = this.data.size() * width + 250;
    }

    public void showBarLabel (boolean value){
        this.showBarLabel = value;
    }

    public void setAxisMultiplier(int value){
        this.axisMultiplier = value;
    }


    private void drawAxis(Graphics g){
        Graphics2D stroke = (Graphics2D) g;
        stroke.setColor(Color.gray);
        stroke.setStroke(new BasicStroke(1));
        int starting = 0;
        for (int i = startingPoint.getY(); i > defaultSpacing; i-=defaultSpacing) {
            stroke.drawString(String.valueOf(starting * axisMultiplier), startingPoint.getX() - defaultSpacing, i + 5);
            stroke.drawLine(startingPoint.getX() - 20, i, startingPoint.getX() + 20, i);
            starting += defaultSpacing;
        }

        stroke.setStroke(new BasicStroke(5));
        stroke.drawLine(startingPoint.getX(), startingPoint.getY() - starting, startingPoint.getX(), startingPoint.getY());
    }

    private void renderBarLabel(Graphics g, String value, int x, int y){
        if (this.showBarLabel){
            g.setColor(Color.black);
            g.setFont(this.textFont);
            g.drawString(value, x, y);
        }
    }

    private void renderChart(Graphics g){
        this.legends = new LinkedHashMap<>();
        boolean noColours = colors.isEmpty();


        int offSet = startingPoint.getX() + 50;
        int colorPicker = 0;


        for (Map.Entry<String, Double> entry : this.data.entrySet()){

            renderBarLabel(g, entry.getKey(), offSet, startingPoint.getY() - entry.getValue().intValue() - 20);

            if (noColours){
                g.setColor(randomColourPicker());
            }else {
                colorPicker = this.colors.size() == colorPicker ? 0 : colorPicker;
                g.setColor(this.colors.get(colorPicker));
                colorPicker+= 1;
            }

            legends.put(String.format("%s %.2f", entry.getKey(), entry.getValue()), g.getColor());
            g.fillRect(offSet, startingPoint.getY() - (entry.getValue().intValue() / axisMultiplier),columnWidth,(entry.getValue().intValue() / axisMultiplier));
            offSet += columnWidth + 10;
        }
        addLegend();
    }

    private void addLegend(){
        Legend legendLayout = new Legend(legends);
        legendLayout.setHorizontalSpacing(80);
        legendLayout.setXYStartingPoint(this.legendXYPosition.getX(), this.legendXYPosition.getY());
        add(legendLayout);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxis(g);
        drawChartTitle(g);
        renderChart(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(finalWidth, 1000);
    }
}

