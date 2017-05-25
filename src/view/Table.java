package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Maria on 22.05.2017.
 */
class Table {
    private JPanel tablePanel = new JPanel();
    private DefaultTableModel tableModel;
    private JTable myTable;
    private JScrollPane scrollPane;

    Table(){
        tablePanel.setPreferredSize(new Dimension(150,450));
        String[] tableHeaders = {"N", "T"};
        tableModel = new DefaultTableModel(tableHeaders,0);
    }

    void createTable(){

        myTable = new JTable(tableModel);

        TableColumnModel columnModel = myTable.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(65);
        columnModel.getColumn(0).setMinWidth(65);
        columnModel.getColumn(1).setMaxWidth(65);
        columnModel.getColumn(1).setMinWidth(65);


        scrollPane = new JScrollPane(myTable);
        scrollPane.setPreferredSize(new Dimension(150,450));

        tablePanel.add(scrollPane);
        tablePanel.setVisible(true);
        scrollPane.setVisible(true);
    }

    JPanel getTable(){
        return tablePanel;
    }

    boolean checkTable(){
        return tableModel.getRowCount() != 0;
    }

    void addRow(Vector<Integer> vector){
        tableModel.addRow(vector);
    }

    public void updateTable() {
        tablePanel.removeAll();
        createTable();
        tablePanel.updateUI();
    }
}
