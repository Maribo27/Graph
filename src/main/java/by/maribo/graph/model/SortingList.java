package by.maribo.graph.model;

import java.util.*;

/**
 * Created by Maria on 25.05.2017.
 */
public class SortingList {
    private List<SortingTime> timeOfSorting;

    public SortingList(){
        timeOfSorting = new ArrayList<>();
    }

    public void setTimes(List<SortingTime> timeList) {
        this.timeOfSorting = timeList;
    }

    public List<SortingTime> getTimes() {
        return timeOfSorting;
    }
}