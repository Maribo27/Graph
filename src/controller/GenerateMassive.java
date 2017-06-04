package controller;

import model.SortingTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maria on 23.05.2017.
 */
class GenerateMassive {
    private int maxNumberOfElements, step;
    private List<SortingTime> times;
    private SortingTime avTime;

    GenerateMassive(int n, int sh) {
        this.maxNumberOfElements = n;
        this.step = sh;
        times = new ArrayList<>();
        avTime = null;
    }

    private List<Integer> generating(int size) {
        List<Integer> newArray = new ArrayList<>();

        for (int elemCount = 0; elemCount < size; elemCount++) {
            int element = ((int) (Math.random() * 100) - 50);
            newArray.add(element);
        }
        return newArray;
    }

    List<SortingTime> generate() {
        int remainder = maxNumberOfElements % step;
        int ceil = ((int) Math.ceil(maxNumberOfElements / step));
        for (int countMassive = 0; countMassive < ceil; countMassive++) {
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

            avTime = new SortingTime((countMassive + 1) * step, averageTime);
            times.add(avTime);
        }
        int averageTime = 0;

        if (remainder != 0) {

            for (int arrayNumber = 0; arrayNumber < 1000; arrayNumber++) {
                List<Integer> array = generating(maxNumberOfElements);
                int timeA = (int) System.nanoTime() / 1000;
                QSort qSort = new QSort(array);
                qSort.sort(0, array.size() - 1);
                int timeB = (int) System.nanoTime() / 1000;
                int timeDelta = timeB - timeA;
                averageTime += timeDelta;
            }
            averageTime = averageTime / 1000;

            avTime = new SortingTime(maxNumberOfElements, averageTime);
            times.add(avTime);
        }

        return times;
    }
}
