package model;

import java.util.Vector;

/**
 * Created by Maria on 25.05.2017.
 */
public class TimeArray {
    private Vector<Vector<Integer>> times = new Vector<>(2);
    public TimeArray(){
        times.add(new Vector<>());
        times.add(new Vector<>());
    }

    void setTimes(int element, int time){
        times.elementAt(0).add(element);
        times.elementAt(1).add(time);
    }

    public Vector<Vector<Integer>> returnTime(){
        return times;
    }
}
