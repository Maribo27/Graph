package by.maribo.graph.view;

import by.maribo.graph.model.SortingTime;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by Maria on 30.05.2017.
 */
public class TableModel extends AbstractTableModel {

    private String[] tableHeaders = {"N", "T"};
    private List<SortingTime> times;
    private static final int NUMBER_OF_ELEMENTS = 0;
    private static final int TIME = 1;

    TableModel(List<SortingTime> times){
        this.times = times;
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return tableHeaders[column];
    }


    @Override
    public int getRowCount() {
        return times.size();
    }

    @Override
    public int getColumnCount() {
        return tableHeaders.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        SortingTime time = times.get(rowIndex);

        switch (columnIndex) {
            case NUMBER_OF_ELEMENTS:
                result = time.getNumberOfElements();
                break;
            case TIME:
                result = time.getTime();
                break;
        }
        return result;
    }
}