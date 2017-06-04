package view;

import controller.MyMouseAdapter;
import model.SortingTime;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static view.Const.BORDER;
import static view.Const.BORDER_UP;


/**
 * Created by Maria on 23.05.2017.
 */
public class GraphicPanel extends JPanel {

    private int zoom, lastX, lastY, countOfSegments, countOfSegmentsY;
    private int SEGMENT = 40;
    private int segX, segY;
    private List<SortingTime> points;

    public GraphicPanel(int zoom, List<SortingTime> points, int numberOfArrays) {

        this.zoom = zoom;
        this.points = points;

        countOfSegments = (int) Math.ceil(numberOfArrays / this.zoom) + 1;

        countOfSegmentsY = 5;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(620, 448));
        setSize(new Dimension(620, 448));

        segX = (int) Math.ceil(getWidth() - BORDER_UP - BORDER) / countOfSegments + 1;
        segY = (int) Math.ceil(getHeight() - BORDER - BORDER_UP) * 2 / countOfSegmentsY;

        addMouseWheelListener(e -> {

            if (e.getWheelRotation() < 0) {
                setSize((int) Math.floor(getWidth() + 20),
                        (int) Math.floor(getHeight() + 20));
                System.out.println(getWidth());
                System.out.println(getHeight());
                setPreferredSize(new Dimension((int) Math.floor(getWidth() + 20),
                        (int) Math.floor(getHeight() + 20)));
                segX = (int) Math.ceil(getWidth() - BORDER_UP - BORDER) / countOfSegments + 1;
                segY = (int) Math.ceil(getHeight() - BORDER - BORDER_UP) * 2 / countOfSegmentsY;
                repaint();
            } else if (e.getWheelRotation() > 0 && getHeight() > 448) {
                setSize((int) Math.floor(getWidth() - 20),
                        (int) Math.floor(getHeight() - 20));
                System.out.println(getWidth());
                System.out.println(getHeight());
                setPreferredSize(new Dimension((int) Math.floor(getWidth() - 20),
                        (int) Math.floor(getHeight() - 20)));
                segX = (int) Math.ceil(getWidth() - BORDER_UP - BORDER) / countOfSegments + 1;
                segY = (int) Math.ceil(getHeight() - BORDER - BORDER_UP) * 2 / countOfSegmentsY;
                repaint();
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        lastX = BORDER;
        lastY = getHeight() - BORDER;

        g.drawLine(BORDER, BORDER_UP, BORDER, getHeight() - BORDER);
        g.drawLine(BORDER, BORDER_UP, BORDER - 5, 12);
        g.drawLine(BORDER, BORDER_UP, BORDER + 5, 12);

        g.drawLine(BORDER, getHeight() - BORDER, getWidth() - BORDER_UP, getHeight() - BORDER);
        g.drawLine(getWidth() - BORDER_UP, getHeight() - BORDER, getWidth() - 12, getHeight() - BORDER + 5);
        g.drawLine(getWidth() - BORDER_UP, getHeight() - BORDER, getWidth() - 12, getHeight() - BORDER - 5);

        int segmentName = zoom;
        for (int x = BORDER + segX; x < countOfSegments * segX; x += segX) {
            g.drawLine(x, getHeight() - BORDER - 5, x, getHeight() - BORDER + 5);
            g.drawString(Integer.toString(segmentName), x - 5, getHeight() - 4);
            segmentName += zoom;
        }
        segmentName = zoom / 5;
        for (int y = getHeight() - BORDER - segY; y > BORDER_UP; y -= segY) {
            g.drawLine(BORDER - 5, y, BORDER + 5, y);
            g.drawString(Integer.toString(segmentName), 5, y + 5);
            segmentName += zoom / 5;
        }

        g.drawString("T, мкс", 40, BORDER - 5);
        g.drawString("N", getWidth() - BORDER + 10, getHeight() - 40);

        if (!points.isEmpty())
            for (int i = 0; i < points.size(); i++) {
                addPoint(points.get(i).getNumberOfElements(), points.get(i).getTime(), g);
            }
    }

    public JPanel getPanel() {

        JScrollPane scroll = new JScrollPane(this);



        MyMouseAdapter adapter = new MyMouseAdapter(scroll);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        JPanel panel = new JPanel();
        panel.add(scroll);
        return panel;
    }

    private void addPoint(int cordX, int cordY, Graphics g) {

        double tempX = cordX * segX / zoom,
                tempY = cordY * segY * 5 / zoom;

        cordX = BORDER + (int) tempX;
        cordY = getHeight() - BORDER - (int) tempY;

        g.drawLine(lastX, lastY, cordX, cordY);
        g.drawOval(cordX - 2, cordY - 2, 4, 4);

        lastX = cordX;
        lastY = cordY;
    }

}
