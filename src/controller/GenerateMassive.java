package controller;

import model.*;

import java.util.*;

/**
 * Created by Maria on 23.05.2017.
 */
class GenerateMassive extends Thread {
    private int maxNumberOfElements, step;
    private List<SortingTime> times;
    private Controller controller;

    GenerateMassive(int n, int sh, Controller controller) {
        this.maxNumberOfElements = n;
        this.step = sh;
        times = new ArrayList<>();
        this.controller = controller;
    }

    @Override
    public void run(){
        int ceil = ((int) Math.ceil(maxNumberOfElements / step));
        for (int countMassive = 0; countMassive <= ceil; countMassive++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int averageTime = 0;
            for (int arrayNumber = 0; arrayNumber < 1000; arrayNumber++) {
                List<Integer> array = generating((countMassive + 1) * step);
                int timeA = (int) System.nanoTime() / 1000;
                QSort qSort = new QSort(array);
                qSort.sort(0, array.size() - 1);
                int timeB = (int) System.nanoTime() / 1000;
                int timeDelta = timeB - timeA;
                averageTime += timeDelta;
            }
            averageTime = averageTime / 1000;

            SortingTime avTime = new SortingTime((countMassive + 1) * step, averageTime);
            times.add(avTime);
            controller.changeData(times);
            controller.updateView();
        }
    }

    private List<Integer> generating(int size) {
        List<Integer> newArray = new ArrayList<>();

        for (int elemCount = 0; elemCount < size; elemCount++) {
            int element = ((int) (Math.random() * 100) - 50);
            newArray.add(element);
        }
        return newArray;
    }
}
