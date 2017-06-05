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

    private int zoom, lastX, lastY, countOfSegments, countOfSegmentsY;
    private int segX, segY, coefY = 200;
    private List<SortingTime> points;

    GraphicPanel(int zoom, List<SortingTime> points, int numberOfArrays) {

        setBackground(Color.WHITE);
        this.zoom = zoom;
        this.points = points;

        if (numberOfArrays <= 100)
            coefY = 1;
        else if (numberOfArrays <= 1000)
            coefY = 5;
        else if (numberOfArrays <= 10000)
            coefY = 25;
        else if (numberOfArrays <= 100000)
            coefY = 100;

        countOfSegments = (int) Math.ceil(numberOfArrays / this.zoom) + 1;

        countOfSegmentsY = 5;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(620, 448));
        setSize(new Dimension(620, 448));

        segX = (int) Math.ceil(getWidth() - BORDER_UP - BORDER - 10) / countOfSegments + 2;
        segY = (int) Math.ceil(getHeight() - BORDER - BORDER_UP  - 20)/ countOfSegmentsY;

        addMouseWheelListener(e -> {

            if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK){

                if (e.getWheelRotation() < 0 && getHeight() < 2000) {
                    setSize((int) Math.floor(getWidth() + 20),
                            (int) Math.floor(getHeight() + 20));
                    setPreferredSize(new Dimension((int) Math.floor(getWidth() + 20),
                            (int) Math.floor(getHeight() + 20)));
                    segX = (int) Math.ceil(getWidth() - BORDER_UP - BORDER - 10) / countOfSegments + 2;
                    segY = (int) Math.ceil(getHeight() - BORDER - BORDER_UP - 20) / countOfSegmentsY;
                    repaint();
                } else if (e.getWheelRotation() > 0 && getHeight() > 448) {
                    setSize((int) Math.floor(getWidth() - 20),
                            (int) Math.floor(getHeight() - 20));
                    setPreferredSize(new Dimension((int) Math.floor(getWidth() - 20),
                            (int) Math.floor(getHeight() - 20)));
                    segX = (int) Math.ceil(getWidth() - BORDER_UP - BORDER - 10) / countOfSegments + 2;
                    segY = (int) Math.ceil(getHeight() - BORDER - BORDER_UP - 20) / countOfSegmentsY;
                    repaint();
                }
            }

            if ((e.getModifiers() & InputEvent.ALT_MASK) == InputEvent.ALT_MASK){

                if (e.getWheelRotation() < 0 && getWidth() < 3000) {
                    setSize((int) Math.floor(getWidth() + 20),
                            (int) Math.floor(getHeight()));
                    setPreferredSize(new Dimension((int) Math.floor(getWidth() + 20),
                            (int) Math.floor(getHeight())));
                    segX = (int) Math.ceil(getWidth() - BORDER_UP - BORDER - 10) / countOfSegments + 2;
                    repaint();
                } else if (e.getWheelRotation() > 0 && getWidth() > 620) {
                    setSize((int) Math.floor(getWidth() - 20),
                            (int) Math.floor(getHeight()));
                    setPreferredSize(new Dimension((int) Math.floor(getWidth() - 20),
                            (int) Math.floor(getHeight())));
                    segX = (int) Math.ceil(getWidth() - BORDER_UP - BORDER - 10) / countOfSegments + 2;
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


        int segmentName = zoom;
        for (int x = BORDER + segX; x < countOfSegments * segX; x += segX) {
            g2.drawLine(x, getHeight() - BORDER - 5, x, getHeight() - BORDER + 5);
            float[] dashPattern = {2.0f, 5.0f};
            g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0));
            g2.drawLine(x, getHeight() - BORDER, x, BORDER_UP);
            g2.drawString(Integer.toString(segmentName), x - 5, getHeight() - 4);
            g2.setStroke(new BasicStroke(2.0f));
            segmentName += zoom;
        }
        segmentName = coefY;
        for (int y = getHeight() - BORDER - segY; y > BORDER_UP; y -= segY) {
            g2.drawLine(BORDER - 5, y, BORDER + 5, y);
            float[] dashPattern = {2.0f, 5.0f};
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

        JScrollPane scroll = new JScrollPane(this);

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

}
