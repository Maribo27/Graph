package by.maribo.graph.view;

import by.maribo.graph.controller.Controller;
import by.maribo.graph.model.SortingTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

import static by.maribo.graph.view.Const.*;

/**
 * Created by Maria on 23.05.2017.
 */
public class GraphicPanel extends JPanel {

    private int scaleDimension, lastX, lastY;
    private int countOfSegmentsY, countOfSegmentsX;
    private int segmentX, segmentY, coefficientY, scaleState, scaleXState;
    private List<SortingTime> points;
    private Controller controller;

    GraphicPanel(int scaleDimension, List<SortingTime> points, int numberOfArrays, int coefficientY, int width, int height, Controller controller, int scaleState, int scaleXState) {

        setBackground(Color.WHITE);
        this.scaleXState = scaleXState;
        this.scaleState = scaleState;
        this.controller = controller;
        this.scaleDimension = scaleDimension;
        this.points = points;
        if (coefficientY < 10 & coefficientY % 5 != 0) this.coefficientY = 2;
        else this.coefficientY = coefficientY / 5;
        changeSize(width, height);

        countOfSegmentsX = numberOfArrays / this.scaleDimension;
        countOfSegmentsY = 5;
        if (coefficientY > 10 & coefficientY % 5 != 0) countOfSegmentsY++;

        segmentX = (getWidth() - BORDER_SEGMENT - BORDER_LEFT) / countOfSegmentsX;
        segmentY = (getHeight() - BORDER_SEGMENT - BORDER) / countOfSegmentsY;

        addMouseWheelListener(e -> {
            if (e.getModifiers() == InputEvent.CTRL_MASK){
                if (e.getWheelRotation() < 0 && this.scaleState < 100) {
                    this.scaleState++;
                    changeSize(getWidth() + 20, getHeight() + 20);
                    segmentX = (getWidth() - BORDER_SEGMENT - BORDER_LEFT) / countOfSegmentsX;
                    segmentY = (getHeight() - BORDER_SEGMENT - BORDER) / countOfSegmentsY;
                    repaint();
                } else if (e.getWheelRotation() > 0 && this.scaleState > 1) {
                    this.scaleState--;
                    changeSize(getWidth() - 20, getHeight() - 20);
                    segmentX = (getWidth() - BORDER_SEGMENT - BORDER_LEFT) / countOfSegmentsX;
                    segmentY = (getHeight() - BORDER_SEGMENT - BORDER) / countOfSegmentsY;
                    repaint();
                }
            }
            else if (e.getModifiers() == InputEvent.ALT_MASK){
                if (e.getWheelRotation() < 0 && this.scaleXState < 100) {
                    this.scaleXState++;
                    changeSize(getWidth() + 100, getHeight());
                    segmentX = (getWidth() - BORDER_SEGMENT - BORDER_LEFT) / countOfSegmentsX;
                    repaint();
                } else if (e.getWheelRotation() > 0 && this.scaleXState > 1) {
                    this.scaleXState--;
                    changeSize(getWidth() - 100, getHeight());
                    segmentX = (getWidth() - BORDER_SEGMENT - BORDER_LEFT) / countOfSegmentsX;
                    repaint();
                }
            }
            getParent().dispatchEvent(e);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        lastX = BORDER_LEFT;
        lastY = getHeight() - BORDER;

        g2.setStroke(new BasicStroke(2.0f));

        g2.drawLine(BORDER_LEFT, BORDER_UP, BORDER_LEFT, getHeight() - BORDER);
        g2.drawLine(BORDER_LEFT, BORDER_UP, BORDER_LEFT - 5, 12);
        g2.drawLine(BORDER_LEFT, BORDER_UP, BORDER_LEFT + 5, 12);

        g2.drawLine(BORDER_LEFT, getHeight() - BORDER, getWidth() - BORDER_UP, getHeight() - BORDER);
        g2.drawLine(getWidth() - BORDER_UP, getHeight() - BORDER, getWidth() - 12, getHeight() - BORDER + 5);
        g2.drawLine(getWidth() - BORDER_UP, getHeight() - BORDER, getWidth() - 12, getHeight() - BORDER - 5);

        float[] dashPattern = {2.0f, 5.0f};
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0));
        g2.drawString("0", BORDER_LEFT - 15, getHeight() - BORDER + 15);
        g2.setStroke(new BasicStroke(2.0f));

        int segmentName = scaleDimension;
        for (int countX = 1; countX <= countOfSegmentsX; countX++) {
            int x = BORDER_LEFT + segmentX * countX;
            g2.drawLine(x, getHeight() - BORDER - 5, x, getHeight() - BORDER + 5);
            dashPattern = new float[]{2.0f, 5.0f};
            g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0));
            g2.drawLine(x, getHeight() - BORDER, x, BORDER_UP);
            g2.drawString(Integer.toString(segmentName), x - 5, getHeight() - 4);
            g2.setStroke(new BasicStroke(2.0f));
            segmentName += scaleDimension;
        }
        segmentName = coefficientY;
        for (int countY = 1; countY <= countOfSegmentsY; countY++) {
            int numberDist = (String.valueOf(segmentName).length() + 1) * 10;
            int y = getHeight() - BORDER - segmentY * countY;
            g2.drawLine(BORDER_LEFT - 5, y, BORDER_LEFT + 5, y);
            dashPattern = new float[]{2.0f, 5.0f};
            g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0));
            g2.drawLine(BORDER_LEFT, y, getWidth() - BORDER_UP, y);
            g2.drawString(Integer.toString(segmentName), BORDER_LEFT - numberDist, y + 5);
            g2.setStroke(new BasicStroke(2.0f));
            segmentName += coefficientY;
        }

        g2.drawString("T, мкс", BORDER_LEFT + 10, BORDER_UP + 15);
        g2.drawString("N", getWidth() - BORDER_SEGMENT, getHeight() - BORDER - 20);

        if (!points.isEmpty())
            for (SortingTime point : points) {
                try {
                    addPoint(point.getNumberOfElements(), point.getTime(), g2);
                }
                catch (Exception e){
                    System.out.println("Невозможно добавить данную точку");
                }
            }
    }

    JPanel getPanel() {
        return this;
    }

    private void addPoint(int cordX, int cordY, Graphics2D g2) {

        double tempX = cordX * segmentX / scaleDimension,
                tempY = cordY * segmentY / coefficientY;

        cordX = BORDER_LEFT + (int) tempX;
        cordY = getHeight() - BORDER - (int) tempY;

        g2.setColor(new Color (0xFFF4A460, true));
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawLine(lastX, lastY, cordX, cordY);
        g2.drawOval(cordX - 2, cordY - 2, 4, 4);
        g2.fillOval(cordX - 2, cordY - 2, 4, 4);
        lastX = cordX;
        lastY = cordY;
    }

    void sizeIncrement(int sign){
        scaleState += sign * 5;
        changeSize(getWidth() + sign * 100, getHeight() + sign * 100);
        segmentX = (getWidth() - BORDER_SEGMENT - BORDER_LEFT) / countOfSegmentsX;
        segmentY = (getHeight() - BORDER_SEGMENT - BORDER) / countOfSegmentsY;
        repaint();
    }

    private void changeSize(int width, int height){
        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        repaint();
        controller.changeSize(width, height, this.scaleState, this.scaleXState);
    }

    void clearScale(){
        this.scaleState = 1;
        changeSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        segmentX = (getWidth() - BORDER_SEGMENT - BORDER_LEFT) / countOfSegmentsX;
        segmentY = (getHeight() - BORDER_SEGMENT - BORDER) / countOfSegmentsY;
        repaint();
    }
}