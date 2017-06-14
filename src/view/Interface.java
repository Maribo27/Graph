package view;

import controller.*;
import static view.Const.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * Created by Maria on 22.05.2017.
 */
public class Interface {

    private JFrame mainWindow = new JFrame("Лабораторная работа #3");
    private JPanel mainPanel = new JPanel();
    private Table table;
    private JButton plusButton, minusButton, scaleButton;

    private int zoomPercentage;
    private JLabel scaleLabel;
    private JPanel graphic = new JPanel();
    private GraphicPanel graphicPanel;
    private JScrollPane scrollPane;
    private Controller controller;
    private JButton buildButton;
    private int numberOfArrays, zoom, width = WINDOW_WIDTH, height = WINDOW_HEIGHT;

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

        graphicPanel = new GraphicPanel(1, new ArrayList<>(), 10, 5, this.width, this.height, controller, 1);

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
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));
        JPanel parameterPanel = new JPanel();
        parameterPanel.setMaximumSize(new Dimension(600, 50));
        parameterPanel.setPreferredSize(new Dimension(600, 50));
        parameterPanel.setSize(new Dimension(600, 50));
        parameterPanel.setLayout(new GridLayout(2, 2));
        JLabel labelZoom = new JLabel("Кратность масштаба");
        JTextField textFieldZoom = new JTextField();

        JLabel labelNumber = new JLabel("Максимальное число элементов");
        JTextField textFieldNumber = new JTextField();

        buildButton = new JButton("Построить график");
        buildButton.addActionListener((ActionEvent e) -> {
            String checkNumber = textFieldNumber.getText(),
                    checkZoom = textFieldZoom.getText();

            boolean validating = checkNumber.matches("\\D+") || checkZoom.matches("\\D+")
                    || (Integer.parseInt(checkNumber) < Integer.parseInt(checkZoom)) || (Integer.parseInt(checkZoom) < 2);

            if (validating) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Введены некорректные данные", "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
                textFieldNumber.setText("");
                textFieldZoom.setText("");
                return;
            }

            numberOfArrays = Integer.parseInt(checkNumber);
            zoom = Integer.parseInt(checkZoom);

            if (numberOfArrays / zoom > 20) {
                int answer = JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(), "Введена слишком маленькая кратность, " +
                                "график будет нечитабельным, вы уверены?" +
                                "Для растяжения оси Ох: Alt + колёсико мышки", "Ошибка",
                        JOptionPane.YES_NO_OPTION);

                if (answer == JOptionPane.NO_OPTION)
                {
                    textFieldZoom.setText("");
                    return;
                }
            }
            controller.generateMassive(numberOfArrays,zoom);
        });

        JPanel zoomPanel = new JPanel();
        plusButton = new JButton("+");
        minusButton = new JButton("-");
        scaleButton = new JButton("Сбросить масштаб");
        scaleButton.setEnabled(false);
        zoomPercentage = 0;
        scaleLabel = new JLabel("Масштаб: " + zoomPercentage + "%");
        plusButton.addActionListener(e -> graphicPanel.sizeIncrement(1));

        minusButton.addActionListener(e -> graphicPanel.sizeIncrement(-1));
        minusButton.setEnabled(false);

        scaleButton.addActionListener(e -> graphicPanel.clearScale());

        zoomPanel.add(buildButton);
        zoomPanel.add(minusButton);
        zoomPanel.add(scaleLabel);
        zoomPanel.add(plusButton);

        zoomPanel.add(scaleButton);

        //JPanel newPanel = new JPanel();
        //newPanel.add(buildButton);

        parameterPanel.add(labelZoom);
        parameterPanel.add(textFieldZoom);
        parameterPanel.add(labelNumber);
        parameterPanel.add(textFieldNumber);
        //parameterPanel.add(newPanel);
        tempPanel.add(parameterPanel);
        tempPanel.add(zoomPanel);

        return tempPanel;
    }

    public void updateMainFrame(){
        mainPanel.remove(table.getTable());
        table.changeData(controller.getModel().getTimes());
        table.updateTable();
        mainPanel.add(table.getTable(), BorderLayout.WEST);
        mainPanel.remove(scrollPane);
        graphicPanel = new GraphicPanel(zoom, controller.getModel().getTimes(), numberOfArrays, controller.getTimeZoom(), this.width, this.height, controller, zoomPercentage);
        graphic = graphicPanel.getPanel();

        scrollPane = new JScrollPane(graphic, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        MyMouseAdapter adapter = new MyMouseAdapter(scrollPane);
        graphic.addMouseListener(adapter);
        graphic.addMouseMotionListener(adapter);

        mainPanel.add(scrollPane);

        table.updateTable();
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

    public void changeSize(int width, int height, int sizeCoef){
        this.width = width;
        this.height = height;
        zoomPercentage = sizeCoef;
        scaleLabel.setText("Масштаб: " + this.zoomPercentage + "%");
        plusButton.setEnabled(true);
        minusButton.setEnabled(true);
        if (zoomPercentage > 95) plusButton.setEnabled(false);
        if (zoomPercentage <= 5) minusButton.setEnabled(false);
        if (!scaleButton.isEnabled()) scaleButton.setEnabled(true);
        if (zoomPercentage == 1) scaleButton.setEnabled(false);
    }
}
