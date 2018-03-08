package by.maribo.graph.view;

import by.maribo.graph.controller.*;
import static by.maribo.graph.view.Const.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * Created by Maria on 22.05.2017.
 */
public class Interface {

    private Controller controller;
    private GraphicPanel graphicPanel;
    private Table table;

    private JFrame mainWindow = new JFrame("Лабораторная работа #3");
    private JPanel mainPanel = new JPanel(), graphic = new JPanel();
    private JButton plusButton, minusButton, scaleButton, buildButton;
    private JLabel scaleLabel;
    private JScrollPane scrollPane;
    private int zoomPercentage, scaleXState, numberOfArrays, zoom, width = WINDOW_WIDTH, height = WINDOW_HEIGHT;

    public Interface(Controller controller){
        this.controller = controller;
    }

    public void runProgram() {

        initMainFrame();

        mainPanel.setLayout(new BorderLayout());
        table = new Table(new ArrayList<>());
        table.createTable();

        mainPanel.add(table.getTable(), BorderLayout.WEST);
        mainPanel.add(addParameterPanel(), BorderLayout.SOUTH);

        graphicPanel = new GraphicPanel(1, new ArrayList<>(), 10, 5, this.width, this.height, controller, 1, 1);
        graphic = graphicPanel.getPanel();

        scrollPane = new JScrollPane(graphic, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        MyMouseAdapter adapter = new MyMouseAdapter(scrollPane);
        graphic.addMouseListener(adapter);
        graphic.addMouseMotionListener(adapter);

        mainPanel.add(scrollPane);
        mainWindow.add(mainPanel);
        mainWindow.setVisible(true);
        mainWindow.setLocationRelativeTo(null);
    }

    private JPanel addParameterPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JPanel parameterPanel = new JPanel();
        parameterPanel.setMaximumSize(new Dimension(600, 50));
        parameterPanel.setPreferredSize(new Dimension(600, 50));
        parameterPanel.setSize(new Dimension(600, 50));
        parameterPanel.setLayout(new GridLayout(2, 2));

        JLabel labelZoom = new JLabel("Кратность масштаба");
        JTextField textFieldZoom = new JTextField();

        JLabel labelNumber = new JLabel("Максимальное число элементов");
        JTextField textFieldNumber = new JTextField();

        JButton XButton = new JButton("10 делений по Х");
        XButton.setEnabled(false);

        buildButton = new JButton("Построить график");
        buildButton.addActionListener((ActionEvent e) -> {
            clearMainFrame();
            String checkNumber = textFieldNumber.getText(),
                    checkZoom = textFieldZoom.getText();

            boolean validating = checkNumber.matches("[\\s\\D]*") || checkZoom.matches("[\\s\\D]*")
                    || (Integer.parseInt(checkNumber) < Integer.parseInt(checkZoom)) || (Integer.parseInt(checkZoom) < 2);
            if (validating) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Введены некорректные данные", "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            numberOfArrays = Integer.parseInt(checkNumber);
            zoom = Integer.parseInt(checkZoom);

            XButton.setEnabled(false);
            if (numberOfArrays / zoom > 20) {
                int answer = JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(), "Введена слишком маленькая кратность, " +
                                "график будет нечитабельным, вы уверены?" +
                                "\nДля растяжения оси Ох: Alt + колёсико мышки", "Ошибка",
                        JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.NO_OPTION) return;
                XButton.setEnabled(true);
            }
            controller.generateMassive(numberOfArrays,zoom);
        });

        XButton.addActionListener((ActionEvent e) -> {
            zoom = numberOfArrays / 10;
            mainPanel.remove(scrollPane);
            int maxTime = controller.getTimeZoom();
            if (maxTime < 5) maxTime = 5;
            graphicPanel = new GraphicPanel(zoom, controller.getModel().getTimes(), numberOfArrays, maxTime, this.width, this.height, controller, zoomPercentage, scaleXState);
            graphic = graphicPanel.getPanel();

            scrollPane = new JScrollPane(graphic, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            MyMouseAdapter adapter = new MyMouseAdapter(scrollPane);
            graphic.addMouseListener(adapter);
            graphic.addMouseMotionListener(adapter);

            mainPanel.add(scrollPane);
            XButton.setEnabled(false);
            mainWindow.repaint();
            mainWindow.revalidate();
        });

        JPanel toolsPanel = new JPanel();
        plusButton = new JButton("+");
        plusButton.addActionListener(e -> graphicPanel.sizeIncrement(1));
        minusButton = new JButton("-");
        minusButton.addActionListener(e -> graphicPanel.sizeIncrement(-1));
        minusButton.setEnabled(false);
        scaleButton = new JButton("Сбросить масштаб");
        scaleButton.addActionListener(e -> {
            graphicPanel.clearScale();
            scaleButton.setEnabled(false);
        });
        scaleButton.setEnabled(false);
        zoomPercentage = 0;
        scaleLabel = new JLabel("Масштаб: " + zoomPercentage + "%");

        toolsPanel.add(buildButton);
        toolsPanel.add(minusButton);
        toolsPanel.add(scaleLabel);
        toolsPanel.add(plusButton);
        toolsPanel.add(XButton);
        toolsPanel.add(scaleButton);

        parameterPanel.add(labelZoom);
        parameterPanel.add(textFieldZoom);
        parameterPanel.add(labelNumber);
        parameterPanel.add(textFieldNumber);
        bottomPanel.add(parameterPanel);
        bottomPanel.add(toolsPanel);

        return bottomPanel;
    }

    public void updateMainFrame(){
        mainPanel.remove(table.getTable());
        table.changeData(controller.getModel().getTimes());
        table.updateTable();
        mainPanel.add(table.getTable(), BorderLayout.WEST);

        mainPanel.remove(scrollPane);
        int maxTime = controller.getTimeZoom();
        if (maxTime < 5) maxTime = 5;
        graphicPanel = new GraphicPanel(zoom, controller.getModel().getTimes(), numberOfArrays, maxTime, this.width, this.height, controller, zoomPercentage, scaleXState);
        graphic = graphicPanel.getPanel();
        scrollPane = new JScrollPane(graphic, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        MyMouseAdapter adapter = new MyMouseAdapter(scrollPane);
        graphic.addMouseListener(adapter);
        graphic.addMouseMotionListener(adapter);
        mainPanel.add(scrollPane);

        mainWindow.repaint();
        mainWindow.revalidate();
    }

    private void initMainFrame(){
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setPreferredSize(new Dimension(800, 600));
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setResizable(false);
        mainWindow.setEnabled(true);
    }

    public void buttonHide(){
        buildButton.setEnabled(!buildButton.isEnabled());
    }

    public void changeSize(int width, int height, int zoomPercentage, int scaleXState){
        this.width = width;
        this.height = height;
        this.scaleXState = scaleXState;
        this.zoomPercentage = zoomPercentage;
        scaleLabel.setText("Масштаб: " + this.zoomPercentage + "%");
        plusButton.setEnabled(true);
        minusButton.setEnabled(true);
        if (this.zoomPercentage > 95) plusButton.setEnabled(false);
        if (this.zoomPercentage <= 5) minusButton.setEnabled(false);
        if (!scaleButton.isEnabled()) scaleButton.setEnabled(true);
        if (this.zoomPercentage == 1 && scaleXState == 1) scaleButton.setEnabled(false);
    }

    private void clearMainFrame(){
        mainPanel.remove(table.getTable());
        table.changeData(new ArrayList<>());
        table.updateTable();
        mainPanel.add(table.getTable(), BorderLayout.WEST);

        mainPanel.remove(scrollPane);
        graphicPanel = new GraphicPanel(1, new ArrayList<>(), 10, 5, WINDOW_WIDTH, WINDOW_HEIGHT, controller, 1, 1);
        graphic = graphicPanel.getPanel();
        scrollPane = new JScrollPane(graphic, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        MyMouseAdapter adapter = new MyMouseAdapter(scrollPane);
        graphic.addMouseListener(adapter);
        graphic.addMouseMotionListener(adapter);
        mainPanel.add(scrollPane);

        mainWindow.repaint();
        mainWindow.revalidate();
    }
}
