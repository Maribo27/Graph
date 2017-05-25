package model;

import java.util.Vector;

/**
 * Created by Maria on 25.05.2017.
 */
public class Threads extends Thread {
    private int step;
    private int ceil;
    private TimeArray timeArray = new TimeArray();

    Threads(int step, int ceil){
        this.step = step;
        this.ceil = ceil;
    }
    @Override
    public void run() {
        for (int countMassive = 0; countMassive < ceil; countMassive++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

            timeArray.setTimes((countMassive + 1) * step, averageTime);
            System.out.println("X: " + countMassive + " Y: " + averageTime);
        }
    }
    
    private Vector<Integer> generating(int size) {
        Vector<Integer> tempArray = new Vector<>();
        for (int elemCount = 0; elemCount < size; elemCount++) {
            int element = ((int)(Math.random() * 100)- 50);
            tempArray.add(element);
        }
        return tempArray;
    }
}
