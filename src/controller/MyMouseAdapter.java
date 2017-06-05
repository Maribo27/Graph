package controller;

import javax.swing.*;
import java.awt.event.*;

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

        verticalScrollBar.addAdjustmentListener(e1 -> {
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