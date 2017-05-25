package model;

import java.util.Vector;

/**
 * Created by Maria on 25.05.2017.
 */
class TimeArray {
    private Vector<Vector<Integer>> times = new Vector<>(2);

    TimeArray() {
        times.add(new Vector<>());
        times.add(new Vector<>());
    }

    Vector<Vector<Integer>> returnTime(){
        return times;
    }

    void setTimes(int number, int time){
        times.elementAt(0).add(number);
        times.elementAt(1).add(time);
    }

}
