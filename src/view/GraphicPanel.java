package view;

import controller.MyMouseAdapter;
import model.SortingTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

import static view.Const.*;

/**
 * Created by Maria on 23.05.2017.
 */
public class GraphicPanel extends JPanel {

    private int zoom, lastX, lastY, countOfSegmentsY, countOfSegmentsX;
    private int segX, segY, coefY = 200;
    private List<SortingTime> points;

    GraphicPanel(int zoom, List<SortingTime> points, int numberOfArrays, int coefY) {

        setBackground(Color.WHITE);
        this.zoom = zoom;
        this.points = points;
        this.coefY = coefY / 5;

        countOfSegmentsX = numberOfArrays / this.zoom + 1;
        countOfSegmentsY = 6;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(620, 427));
        setSize(new Dimension(620, 427));

        segX = (getWidth() - BORDER_SEGMENT) / countOfSegmentsX;
        segY = (getHeight() - BORDER_SEGMENT) / countOfSegmentsY;

        addMouseWheelListener(e -> {

            if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK){
                if (e.getWheelRotation() < 0 && getHeight() < 1000) {
                    setSize((int) Math.floor(getWidth() + 20),
                            (int) Math.floor(getHeight() + 20));
                    setPreferredSize(new Dimension((int) Math.floor(getWidth() + 20),
                            (int) Math.floor(getHeight() + 20)));
                    segX = (getWidth() - BORDER_SEGMENT) / countOfSegmentsX;
                    segY = (getHeight() - BORDER_SEGMENT) / countOfSegmentsY;
                    repaint();
                } else if (e.getWheelRotation() > 0 && getHeight() > 427) {
                    setSize((int) Math.floor(getWidth() - 20),
                            (int) Math.floor(getHeight() - 20));
                    setPreferredSize(new Dimension((int) Math.floor(getWidth() - 20),
                            (int) Math.floor(getHeight() - 20)));
                    segX = (getWidth() - BORDER_SEGMENT) / countOfSegmentsX;
                    segY = (getHeight() - BORDER_SEGMENT) / countOfSegmentsY;
                    repaint();
                }
            }

            if ((e.getModifiers() & InputEvent.ALT_MASK) == InputEvent.ALT_MASK){
                if (e.getWheelRotation() < 0 && getWidth() < 4270) {
                    setSize((int) Math.floor(getWidth() + 20),
                            (int) Math.floor(getHeight()));
                    setPreferredSize(new Dimension((int) Math.floor(getWidth() + 20),
                            (int) Math.floor(getHeight())));
                    segX = (getWidth() - BORDER_SEGMENT) / countOfSegmentsX;
                    repaint();
                } else if (e.getWheelRotation() > 0 && getWidth() > 620) {
                    setSize((int) Math.floor(getWidth() - 20),
                            (int) Math.floor(getHeight()));
                    setPreferredSize(new Dimension((int) Math.floor(getWidth() - 20),
                            (int) Math.floor(getHeight())));
                    segX = (getWidth() - BORDER_SEGMENT) / countOfSegmentsX;
                    repaint();
                }
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {


        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        lastX = BORDER;
        lastY = getHeight() - BORDER;

        g2.setStroke(new BasicStroke(2.0f));

        g2.drawLine(BORDER, BORDER_UP, BORDER, getHeight() - BORDER);
        g2.drawLine(BORDER, BORDER_UP, BORDER - 5, 12);
        g2.drawLine(BORDER, BORDER_UP, BORDER + 5, 12);

        g2.drawLine(BORDER, getHeight() - BORDER, getWidth() - BORDER_UP, getHeight() - BORDER);
        g2.drawLine(getWidth() - BORDER_UP, getHeight() - BORDER, getWidth() - 12, getHeight() - BORDER + 5);
        g2.drawLine(getWidth() - BORDER_UP, getHeight() - BORDER, getWidth() - 12, getHeight() - BORDER - 5);

        float[] dashPattern = {2.0f, 5.0f};
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0));
        g2.drawString("0", 18, getHeight() - 14);
        g2.setStroke(new BasicStroke(2.0f));int segmentName = zoom;
        for (int countX = 1; countX < countOfSegmentsX; countX++) {
            int x = BORDER + segX * countX;
            g2.drawLine(x, getHeight() - BORDER - 5, x, getHeight() - BORDER + 5);
            dashPattern = new float[]{2.0f, 5.0f};
            g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0));
            g2.drawLine(x, getHeight() - BORDER, x, BORDER_UP);
            g2.drawString(Integer.toString(segmentName), x - 5, getHeight() - 4);
            g2.setStroke(new BasicStroke(2.0f));
            segmentName += zoom;
        }
        segmentName = coefY;
        for (int countY = 1; countY < countOfSegmentsY; countY++) {
            int y = getHeight() - BORDER - segY * countY;
            g2.drawLine(BORDER - 5, y, BORDER + 5, y);
            dashPattern = new float[]{2.0f, 5.0f};
            g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0));
            g2.drawLine(BORDER, y, getWidth() - BORDER_UP, y);
            g2.drawString(Integer.toString(segmentName), 5, y + 5);
            g2.setStroke(new BasicStroke(2.0f));
            segmentName += coefY;
        }

        g2.drawString("T, мкс", 40, BORDER - 5);
        g2.drawString("N", getWidth() - BORDER + 10, getHeight() - 40);

        if (!points.isEmpty())
            for (SortingTime point : points) {
                addPoint(point.getNumberOfElements(), point.getTime(), g2);
            }
    }

    JPanel getPanel() {

        JScrollPane scroll = new JScrollPane(this, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        MyMouseAdapter adapter = new MyMouseAdapter(scroll);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        JPanel panel = new JPanel();
        panel.add(scroll);
        return panel;
    }

    private void addPoint(int cordX, int cordY, Graphics2D g2) {

        double tempX = cordX * segX / zoom,
                tempY = cordY * segY / coefY;

        cordX = BORDER + (int) tempX;
        cordY = getHeight() - BORDER - (int) tempY;

        g2.setColor(new Color (0xFFF4A460, true));
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawLine(lastX, lastY, cordX, cordY);
        g2.drawOval(cordX - 2, cordY - 2, 4, 4);
        g2.fillOval(cordX - 2, cordY - 2, 4, 4);
        lastX = cordX;
        lastY = cordY;
    }

    void changeZoom(int zoom){

        setSize((int) Math.floor(620 + 2 * zoom),
                (int) Math.floor(427 + 2 * zoom));
        setPreferredSize(new Dimension((int) Math.floor(620 + 2 * zoom),
                (int) Math.floor(427 + 2 * zoom)));
        segX = (getWidth() - BORDER_SEGMENT) / countOfSegmentsX;
        segY = (getHeight() - BORDER_SEGMENT) / countOfSegmentsY;
        repaint();
    }

}
