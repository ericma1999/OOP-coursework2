package uk.ac.ucl.passawis.charts;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Legend extends JPanel {
    int column = 3;
    private int squareSize = 20;
    private int horizontalSpacing = 100;
    private int verticalSpacing = 20;
    private Point startingPoint = new Point(0, 0);
    LinkedHashMap<String, Color> legends;

    public Legend(LinkedHashMap<String, Color> legends){
        this.legends = legends;
        setOpaque(false);
    }

    public void setColumnAmount(int columns){
        this.column = columns;
    }

    public void setHorizontalSpacing(int spacing){
        this.horizontalSpacing = spacing;
    }

    public void setXYStartingPoint(int x, int y){
        this.startingPoint = new Point(x, y);
    }

    private void renderLegends(Graphics g){
        int xOffset = startingPoint.getX();
        int yOffset = startingPoint.getY();
        int column = 1;
        for (Map.Entry<String, Color> entry: this.legends.entrySet()){
            g.setColor(entry.getValue());
            g.fillRect(xOffset,yOffset,squareSize,squareSize);

            g.setColor(Color.black);
            int labelStart = xOffset + squareSize + 10;
            g.drawString(entry.getKey(), labelStart, yOffset + 15);
            xOffset += (labelStart + horizontalSpacing - xOffset) + horizontalSpacing;

            column += 1;

            if (column > this.column){
                xOffset = startingPoint.getX();
                yOffset = yOffset + squareSize + verticalSpacing;
                column = 1;
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        renderLegends(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 100);
    }


}
