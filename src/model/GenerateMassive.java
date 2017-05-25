package model;

import java.util.Vector;

/**
 * Created by Maria on 23.05.2017.
 */
public class GenerateMassive {
    private int maxNumberOfElements, step;

    public GenerateMassive(int n, int sh) {
        this.maxNumberOfElements = n;
        this.step = sh;
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
        int remainder = maxNumberOfElements % step;
        int ceil = ((int)Math.ceil(maxNumberOfElements/ step));
        for (int countMassive = 0; countMassive < ceil; countMassive++) {
            int averageTime = 0;
            for (int arrayNumber = 0; arrayNumber < 1000; arrayNumber++)
            {
                Vector<Integer> array = generating((countMassive + 1) * step);
                int timeA = (int)System.nanoTime() / 1000;
                QSort qSort = new QSort(array);
                qSort.sort(0, array.size() - 1);
                int timeB = (int)System.nanoTime() / 1000;
                int timeDelta = timeB - timeA;
                averageTime += timeDelta;
            }
            averageTime = averageTime / 1000;

            times.elementAt(0).add((countMassive + 1) * step);
            times.elementAt(1).add(averageTime);
        }
        int averageTime = 0;

        if (remainder != 0){

            for (int arrayNumber = 0; arrayNumber < 1000; arrayNumber++)
            {
                Vector<Integer> array = generating(maxNumberOfElements);
                int timeA = (int)System.nanoTime() / 1000;
                QSort qSort = new QSort(array);
                qSort.sort(0, array.size() - 1);
                int timeB = (int)System.nanoTime() / 1000;
                int timeDelta = timeB - timeA;
                averageTime += timeDelta;
            }
            averageTime = averageTime / 1000;

            times.elementAt(0).add(maxNumberOfElements);
            times.elementAt(1).add(averageTime);
        }
        return times;
    }
}
