package view;

import model.SortingTime;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Maria on 22.05.2017.
 */
class Table {
    private JPanel tablePanel = new JPanel();
    private List<SortingTime> timeList;

    Table(List<SortingTime> timeList) {
        tablePanel.setPreferredSize(new Dimension(150, 450));
        this.timeList = timeList;
    }

    void createTable() {
        TableModel tableModel = new TableModel(timeList);
        JTable myTable = new JTable(tableModel);

        TableColumnModel columnModel = myTable.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(65);
        columnModel.getColumn(0).setMinWidth(65);
        columnModel.getColumn(1).setMaxWidth(65);
        columnModel.getColumn(1).setMinWidth(65);


        JScrollPane scrollPane = new JScrollPane(myTable);
        scrollPane.setPreferredSize(new Dimension(150, 450));

        tablePanel.add(scrollPane);
        tablePanel.setVisible(true);
        scrollPane.setVisible(true);
    }

    JPanel getTable() {
        return tablePanel;
    }

    void updateTable() {
        tablePanel.removeAll();
        createTable();
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    void changeData(List<SortingTime> data){
        timeList = data;
        updateTable();
    }
}