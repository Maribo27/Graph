package model;

import java.util.*;

/**
 * Created by Maria on 25.05.2017.
 */
public class SortingList {
    private List timeOfSorting;

    public SortingList(){
        timeOfSorting = new ArrayList();
    }

    public void setTimes(List<SortingTime> timeList) {
        this.timeOfSorting = timeList;
    }

    public List getTimes() {
        return timeOfSorting;
    }

}
