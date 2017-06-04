package view;

import controller.Controller;
import model.SortingTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Maria on 22.05.2017.
 */
public class Interface {

    private JFrame mainWindow = new JFrame("Лабораторная работа #3");
    private JPanel mainPanel = new JPanel();
    private Vector<Vector<Integer>> tempMass = new Vector<>();
    private Table table;
    private JPanel graphic = new JPanel();
    private GraphicPanel graphicPanel;
    private Controller controller;

    Interface(Controller controller){
        this.controller = controller;
    }

    void runProgram() {

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

        JLabel labelShag = new JLabel("Шаг размерности массивов");
        JTextField textFieldShag = new JTextField();

        JButton buildButton = new JButton("Построить график");
        buildButton.addActionListener((ActionEvent e) -> {
            String checkNumber = textFieldNumber.getText(),
                    checkZoom = textFieldZoom.getText(),
                    checkStep = textFieldShag.getText();

            boolean validating = checkNumber.matches("\\D+") || checkZoom.matches("\\D+") || checkStep.matches("\\D+")
                    || (Integer.parseInt(checkNumber) <= Integer.parseInt(checkStep)) || (Integer.parseInt(checkStep) < 2);

            if (validating) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Введены некорректные данные", "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
                textFieldNumber.setText("");
                textFieldZoom.setText("");
                textFieldShag.setText("");
                return;
            }

            int numberOfArrays = Integer.parseInt(checkNumber),
                    zoom = Integer.parseInt(checkZoom),
                    step = Integer.parseInt(checkStep);

            controller.generateMassive(numberOfArrays,step);

            mainPanel.remove(table.getTable());
            table.changeData(controller.getModel().getTimes());
            table.updateTable();
            mainPanel.add(table.getTable(), BorderLayout.WEST);
            mainPanel.remove(graphic);
            graphicPanel = new GraphicPanel(zoom, controller.getModel().getTimes(), numberOfArrays);
            graphic = graphicPanel.getPanel();
            mainPanel.add(graphic);

            table.updateTable();

            updateMainFrame();
        });

        parameterPanel.add(labelZoom);
        parameterPanel.add(textFieldZoom);
        parameterPanel.add(labelNumber);
        parameterPanel.add(textFieldNumber);
        parameterPanel.add(labelShag);
        parameterPanel.add(textFieldShag);
        parameterPanel.add(buildButton);

        return parameterPanel;
    }

    public void updateMainFrame(){
        mainWindow.repaint();
        mainWindow.revalidate();
    }

    void initMainFrame(){
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setPreferredSize(new Dimension(800, 600));
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setResizable(false);
        mainWindow.setEnabled(true);
    }
}
