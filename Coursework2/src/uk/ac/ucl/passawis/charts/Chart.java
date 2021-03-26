package uk.ac.ucl.passawis.charts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class Chart extends JPanel{
    private String chartTitle = "Chart Name";
    protected ArrayList<Color> colors;
    private Point chartTitlePos;
    protected Point startingPoint = new Point(300, 600);
    private Font titleFont = null;
    private final Random rand = new Random();
    protected Font textFont = new Font("Arial", Font.BOLD, 50);
    protected Point legendXYPosition = new Point(400, 700);


    protected LinkedHashMap<String, Color> legends = new LinkedHashMap<>();
    protected Map<String, Double> data = new LinkedHashMap<>();

    public void initialise(String chartTitle, Map<String, Double> data){
        this.setLayout(new FlowLayout());
        this.setTitle(chartTitle, 100 / 2, 50);
        this.data = data;
        this.colors = new ArrayList<>();
    }

    public void setTitle(String chartTitle, int xPos, int yPos) {
        this.chartTitle = chartTitle;
        this.chartTitlePos = new Point(xPos, yPos);
    }

    public void setTitle(String chartTitle, Font font, int xPos, int yPos) {
        this.chartTitle = chartTitle;
        this.titleFont = font;
        this.chartTitlePos = new Point(xPos, yPos);
    }

    public void setXYStartingPoint(int xStartingPoint, int yStartingPoint) {
        this.startingPoint = new Point(xStartingPoint, yStartingPoint);
    }

    public void setLegendXYPosition(int xStartingPoint, int yStartingPoint){
        this.legendXYPosition = new Point(xStartingPoint, yStartingPoint);
    }

    public void setTextFont(Font font) {
        this.textFont = font;
    }

    protected void drawChartTitle(Graphics g) {
        if (this.titleFont != null) {
            g.setFont(this.titleFont);
        } else {
            g.setFont(this.textFont);
        }
        g.drawString(this.chartTitle, chartTitlePos.getX(), chartTitlePos.getY());
    }

    public void setColours(List<Color> colours){
        this.colors = new ArrayList<>(colours);
    }

    protected Color randomColourPicker(){
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        Color randomColor = new Color(r, g, b);
        colors.add(randomColor);
        return randomColor;
    }

}
