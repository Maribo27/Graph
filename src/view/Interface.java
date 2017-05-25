package view;
import controller.GraphicPanel;
import model.GenerateMassive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

/**
 * Created by Maria on 22.05.2017.
 */
class Interface {

    private JFrame mainWindow = new JFrame("Лабораторная работа #3");
    private JPanel mainPanel = new JPanel();
    private Vector<Vector<Integer>> tempMass = new Vector<>();
    private Table table = new Table();
    private JPanel graphic = new JPanel();
    private GraphicPanel graphicPanel;


    void runProgram(){
        mainWindow.setLayout(new BorderLayout());
        mainWindow.setPreferredSize(new Dimension(800,600));
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.pack();
        mainWindow.setResizable(false);
        mainWindow.setEnabled(true);

        mainPanel.setLayout(new BorderLayout());
        table.createTable();

        mainPanel.add(table.getTable(), BorderLayout.WEST);
        mainPanel.add(addParameterPanel(), BorderLayout.SOUTH);

        tempMass.add(new Vector<>());
        tempMass.add(new Vector<>());

        graphicPanel = new GraphicPanel(10, tempMass, 200);

        graphic = graphicPanel.getPanel();
        mainPanel.add(graphic);

        mainWindow.add(mainPanel);

        mainWindow.setVisible(true);
        mainWindow.setLocationRelativeTo(null);
    }


    JPanel addParameterPanel(){
        JPanel parameterPanel = new JPanel();
        parameterPanel.setMaximumSize(new Dimension(600,100));
        parameterPanel.setLayout(new GridLayout(4,2));

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
                    checkStep  = textFieldShag.getText();

            if (checkNumber.matches("\\D+") || checkZoom.matches("\\D+") || checkStep .matches("\\D+")
                    || (Integer.parseInt(checkNumber) <= Integer.parseInt(checkStep )) || (Integer.parseInt(checkStep ) < 2))
            {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Введены некорректные данные", "Ошибка",
                        JOptionPane.WARNING_MESSAGE);
                textFieldNumber.setText("");
                textFieldZoom.setText("");
                textFieldShag.setText("");
                return;
            }

            int numberOfArrays = Integer.parseInt(checkNumber),
                    zoom = Integer.parseInt(checkZoom),
                    step  = Integer.parseInt(checkStep);

            GenerateMassive newMass = new GenerateMassive(numberOfArrays, step);
            tempMass = newMass.returnTime();

            mainPanel.remove(table.getTable());
            table = new Table();
            table.updateTable();
            mainPanel.add(table.getTable(), BorderLayout.WEST);

            for (int countPairs = 0; countPairs < tempMass.elementAt(0).size(); countPairs++) {
                Vector<Integer> str = new Vector<>(2);
                str.add(tempMass.elementAt(0).elementAt(countPairs));
                str.add(tempMass.elementAt(1).elementAt(countPairs));
                table.addRow(str);
            }
            mainPanel.remove(graphic);
            graphicPanel = new GraphicPanel(zoom, tempMass, numberOfArrays);
            graphic = graphicPanel.getPanel();
            mainPanel.add(graphic);

            table.updateTable();

            mainWindow.repaint();
            mainWindow.revalidate();
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
}
