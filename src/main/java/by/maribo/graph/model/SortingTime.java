package by.maribo.graph.model;

/**
 * Created by Maria on 25.05.2017.
 */
public class SortingTime {

    private int numberOfElements;
    private int time;

    public SortingTime(int numberOfElements, int time) {
        this.numberOfElements = numberOfElements;
        this.time = time;
    }

    public int getTime(){
        return time;
    }

    public int getNumberOfElements(){
        return numberOfElements;
    }
}