package controller;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static controller.Const.*;

/**
 * Created by Maria on 23.05.2017.
 */
public class GraphicPanel extends JPanel{

    private int zoom, lastX, lastY, countOfSegments;
    private int SEGMENT = 40;
    private Vector<Vector<Integer>> points;
    private int numberOfArrays;

    public GraphicPanel(int zoom, Vector<Vector<Integer>> points, int numberOfArrays) {

        this.zoom = zoom;
        this.points = points;
        this.numberOfArrays = numberOfArrays;

        countOfSegments = (int)Math.ceil(this.numberOfArrays / this.zoom);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(620, 448));
        setSize(new Dimension(620, 448));

        addMouseWheelListener(e -> {
            if (e.getWheelRotation() < 0) {
                setSize((int) Math.floor(getWidth() + 20),
                        (int) Math.floor(getHeight() + 20));
                setPreferredSize(new Dimension((int) Math.floor(getWidth() + 20),
                        (int) Math.floor(getHeight() + 20)));
                SEGMENT += 20;
                repaint();
            } else if (e.getWheelRotation() > 0 && getHeight() > 448) {
                setSize((int) Math.floor(getWidth() - 20),
                        (int) Math.floor(getHeight() - 20));
                setPreferredSize(new Dimension((int) Math.floor(getWidth() - 20),
                        (int) Math.floor(getHeight() - 20)));
                SEGMENT -= 20;
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

        g.drawLine(BORDER, getHeight() - BORDER, getWidth() - BORDER_UP , getHeight() - BORDER);
        g.drawLine(getWidth() - BORDER_UP, getHeight() - BORDER, getWidth() - 12 , getHeight() - BORDER + 5);
        g.drawLine(getWidth() - BORDER_UP, getHeight() - BORDER, getWidth() - 12 , getHeight() - BORDER - 5);

        int segmentName = zoom;
        for (int x = BORDER + SEGMENT; x < countOfSegments * SEGMENT; x += SEGMENT) {
            g.drawLine(x,getHeight() - BORDER - 5, x,getHeight() - BORDER + 5);
            g.drawString(Integer.toString(segmentName), x - 5, getHeight() - 4);
            segmentName += zoom;
        }
        segmentName = zoom / 5;
        for (int y = getHeight() - BORDER - SEGMENT; y > BORDER_UP; y -= SEGMENT) {
            g.drawLine(BORDER - 5,y, BORDER + 5,y);
            g.drawString(Integer.toString(segmentName), 5, y + 5);
            segmentName += zoom / 5;
        }

        g.drawString("T, мкс", 30, BORDER - 5);
        g.drawString("N", getWidth() - BORDER + 5, getHeight() - 5);

        if (!points.isEmpty())
            for (int i = 0; i < points.elementAt(0).size(); i++) {
                addPoint(points.elementAt(0).elementAt(i), points.elementAt(1).elementAt(i), g);
            }
    }

    public JPanel getPanel(){

        JScrollPane scroll = new JScrollPane(this);
        MyMouseAdapter adapter = new MyMouseAdapter(scroll);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        JPanel panel = new JPanel();
        panel.add(scroll);
        return panel;
    }

    public void clearGraph() {
        lastX = BORDER;
        lastY = getHeight() - BORDER;
        points.clear();
        repaint();
    }

    public void addPoint(int cordX, int cordY, Graphics g) {

        double tempX = cordX * SEGMENT / zoom,
                tempY = cordY * SEGMENT * 5 / zoom;

        cordX = BORDER + (int) tempX;
        cordY = getHeight() - BORDER - (int)tempY;

        g.drawLine(lastX, lastY, cordX, cordY);
        g.drawOval(cordX - 2, cordY  - 2, 4, 4);

        lastX = cordX;
        lastY = cordY;
    }

}
