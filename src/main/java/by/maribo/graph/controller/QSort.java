package by.maribo.graph.controller;

import java.util.List;

/**
 * Created by Maria on 22.05.2017.
 */
class QSort {

    private List<Integer> array;

    QSort(List<Integer> array) {
        this.array = array;
    }

    void sort(int left, int right) {

        int leftCounter = left,
                rightCounter = right,
                tmp;

        int centerValue = array.get(((left + right) / 2));

        while (leftCounter <= rightCounter) {
            while (array.get(leftCounter) < centerValue)
                leftCounter++;
            while (array.get(rightCounter) > centerValue)
                rightCounter--;
            if (leftCounter <= rightCounter) {
                tmp = array.get(leftCounter);
                array.set(leftCounter, array.get(rightCounter));
                array.set(rightCounter, tmp);
                leftCounter++;
                rightCounter--;
            }
        }

        if (left < rightCounter)
            sort(left, rightCounter);
        if (right < leftCounter)
            sort(leftCounter, right);
    }
}