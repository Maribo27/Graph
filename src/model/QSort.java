package model;

import java.util.Vector;

/**
 * Created by Maria on 22.05.2017.
 */
class QSort {

    private Vector<Integer> array = new Vector<>();

    QSort(Vector<Integer> array){
        this.array = array;
    }

    void sort(int left, int right){

        int leftCounter = left,
                rightCounter = right,
                tmp;
        int centerValue = this.array.elementAt(((left + right) / 2));

        while (leftCounter <= rightCounter) {
            while (this.array.elementAt(leftCounter) < centerValue)
                leftCounter++;
            while (this.array.elementAt(rightCounter) > centerValue)
                rightCounter--;
            if (leftCounter <= rightCounter) {
                tmp = this.array.elementAt(leftCounter);
                this.array.setElementAt(this.array.elementAt(rightCounter), leftCounter);
                this.array.setElementAt(tmp, rightCounter);
                leftCounter++;
                rightCounter--;
            }
        }

        if (left < rightCounter)
            sort(left, rightCounter);
        if (right < leftCounter)
            sort(leftCounter,right);
    }
}