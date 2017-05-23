package view;

import model.GenerateMassive;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Maria on 22.05.2017.
 */
class Interface {

    private JFrame mainWindow = new JFrame("Лабораторная работа #3");
    private JPanel mainPanel = new JPanel();
    private JPanel workPanel = new JPanel();
    private Table table = new Table();
    private int zoom;


    void runProgram(){
        mainWindow.setLayout(new FlowLayout());
        mainWindow.setPreferredSize(new Dimension(800,600));
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        workPanel.setLayout(new BoxLayout(workPanel, BoxLayout.Y_AXIS));
        mainPanel.add(table.getTable());

        JPanel graphic = new JPanel();
        graphic.setMinimumSize(new Dimension(600,350));

        JPanel parameterPanel = new JPanel();
        parameterPanel.setLayout(new BoxLayout(parameterPanel, BoxLayout.X_AXIS));

        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));
        JLabel labelZoom = new JLabel("Кратность масштаба");
        JTextField textFieldZoom = new JTextField();
        JLabel labelNumber = new JLabel("Введите максимальное число элементов");
        JTextField textFieldNumber = new JTextField();
        firstPanel.add(labelZoom);
        firstPanel.add(textFieldZoom);
        firstPanel.add(labelNumber);
        firstPanel.add(textFieldNumber);

        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.Y_AXIS));
        JLabel labelShag = new JLabel("Шаг размерности массивов");
        JTextField textFieldShag = new JTextField();
        JButton buildButton = new JButton("Построить график");
        secondPanel.add(labelShag);
        secondPanel.add(textFieldShag);
        secondPanel.add(buildButton);


        parameterPanel.add(firstPanel);
        parameterPanel.add(secondPanel);

        buildButton.addActionListener(e -> {
            String checkNumber = textFieldNumber.getText();
            String checkZoom = textFieldZoom.getText();
            String checkShag = textFieldShag.getText();
            if (checkNumber.matches("\\D+") || checkZoom.matches("\\D+")
                    || checkShag.matches("\\D+")
                    || (Integer.parseInt(checkNumber) < Integer.parseInt(checkShag)))
            return;

            zoom = Integer.parseInt(checkZoom);

            GenerateMassive newMass = new GenerateMassive(Integer.parseInt(checkNumber), Integer.parseInt(checkShag));
            Vector<Vector<Integer>> tempMass = newMass.returnTime();
            if (table.checkTable()) {
                table = new Table();
                mainPanel.revalidate();
                mainPanel.repaint();
            }
            for (int countPairs = 0; countPairs < tempMass.elementAt(0).size(); countPairs++) {
                Vector<Integer> str = new Vector<>(2);
                str.add(tempMass.elementAt(0).elementAt(countPairs));
                str.add(tempMass.elementAt(1).elementAt(countPairs));
                table.addRow(str);
            }
            table.setTable();


            mainWindow.repaint();
            mainWindow.revalidate();


        });

        workPanel.add(graphic);
        workPanel.add(parameterPanel);
        mainPanel.add(workPanel);


        mainPanel.add(parameterPanel);
        mainWindow.add(mainPanel);
        mainWindow.pack();
        mainWindow.setResizable(false);
        mainWindow.setEnabled(true);
        mainWindow.setVisible(true);
        mainWindow.setLocationRelativeTo(null);

    }
}
