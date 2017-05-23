package model;

import java.util.Vector;

/**
 * Created by Maria on 23.05.2017.
 */
public class GenerateMassive {
    private int maxNumberOfElements, shag;

    public GenerateMassive(int n, int sh) {
        this.maxNumberOfElements = n;
        this.shag = sh;
    }

    Vector<Integer> generating(int size) {
        Vector<Integer> tempArray = new Vector<>();
        for (int elemCount = 0; elemCount < size; elemCount++) {
            int element = ((int)(Math.random() * 100)- 50);
            tempArray.add(element);
        }
        return tempArray;
    }

    public Vector<Vector<Integer>> returnTime(){

        Vector<Vector<Integer>> times = new Vector<>(2);
        times.add(new Vector<>());
        times.add(new Vector<>());
        int ost = maxNumberOfElements % shag;
        int ceil = ((int)Math.ceil(maxNumberOfElements/shag));
        for (int countMassive = 0; countMassive < ceil; countMassive++) {
            Vector<Integer> array = generating((countMassive + 1) * shag);
            long lBegin = System.currentTimeMillis();
            QSort qSort = new QSort(array);
            array = qSort.getNewArray();
            long lEnd = System.currentTimeMillis();
            long lDelta = lEnd - lBegin;
            times.elementAt(0).add((countMassive + 1) * shag);
            times.elementAt(1).add((int)lDelta);
        }

        if (ost != 0){
            Vector<Integer> array = generating(maxNumberOfElements);
            long lBegin = System.currentTimeMillis();
            QSort qSort = new QSort(array);
            array = qSort.getNewArray();
            long lEnd = System.currentTimeMillis();
            long lDelta = lEnd - lBegin;
            times.elementAt(0).add(maxNumberOfElements);
            times.elementAt(1).add((int)lDelta);

        }

        return times;
    }
}
