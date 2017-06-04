package controller;

import javax.swing.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Maria on 23.05.2017.
 */
public class MyMouseAdapter extends MouseAdapter {

    private int prevX, prevY;
    private JScrollPane scrolls;

    public MyMouseAdapter(JScrollPane scrolls) {
        this.scrolls = scrolls;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        int dX = prevX - e.getX();
        int dY = prevY - e.getY();

        JScrollBar verticalScrollBar = scrolls.getVerticalScrollBar();

        verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                System.out.println("test");
            }
        });

        JScrollBar horizontalScrollBar = scrolls.getHorizontalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getValue() + dY);
        horizontalScrollBar.setValue(horizontalScrollBar.getValue() + dX);

        prevX = e.getX();
        prevY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();

    }
}