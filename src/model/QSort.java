package model;

import java.util.Vector;

/**
 * Created by Maria on 22.05.2017.
 */
class QSort {

    private Vector<Integer> array = new Vector<>();


    QSort(Vector<Integer> array){
        this.array = array;
        int left = 0;
        int right = array.size() - 1;
    }

    private void sort(int left, int right){

        int leftCounter = left,
                rightCounter = right,
                tmp;

        int pivot = this.array.elementAt(((left + right) / 2));

        while (leftCounter <= rightCounter) {
            while (this.array.elementAt(leftCounter) < pivot)
                leftCounter++;
            while (this.array.elementAt(rightCounter) > pivot)
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

    Vector<Integer> getNewArray(){
        sort(0, array.size() - 1);
        return array;
    }
}
