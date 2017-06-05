package view;

import controller.Controller;

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
    private JPanel graphic = new JPanel();
    private GraphicPanel graphicPanel;
    private Controller controller;
    private int numberOfArrays, zoom;

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

        graphicPanel = new GraphicPanel(10, new ArrayList<>(), 200);

        graphic = graphicPanel.getPanel();
        mainPanel.add(graphic);

        mainWindow.add(mainPanel);

        mainWindow.setVisible(true);
        mainWindow.setLocationRelativeTo(null);
    }

    private JPanel addParameterPanel() {
        JPanel parameterPanel = new JPanel();
        parameterPanel.setMaximumSize(new Dimension(600, 100));
        parameterPanel.setLayout(new GridLayout(4, 2));

        JLabel labelZoom = new JLabel("Кратность масштаба");
        JTextField textFieldZoom = new JTextField();

        JLabel labelNumber = new JLabel("Максимальное число элементов");
        JTextField textFieldNumber = new JTextField();

        JButton buildButton = new JButton("Построить график");
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


            controller.generateMassive(numberOfArrays,zoom);
        });

        parameterPanel.add(labelZoom);
        parameterPanel.add(textFieldZoom);
        parameterPanel.add(labelNumber);
        parameterPanel.add(textFieldNumber);
        parameterPanel.add(buildButton);

        return parameterPanel;
    }

    public void updateMainFrame(){
        mainPanel.remove(table.getTable());
        table.changeData(controller.getModel().getTimes());
        table.updateTable();
        mainPanel.add(table.getTable(), BorderLayout.WEST);
        mainPanel.remove(graphic);
        graphicPanel = new GraphicPanel(zoom, controller.getModel().getTimes(), numberOfArrays);
        graphic = graphicPanel.getPanel();
        mainPanel.add(graphic);

        table.updateTable(); mainWindow.repaint();
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
}
