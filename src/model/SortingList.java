package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maria on 25.05.2017.
 */
public class SortingList {
    private List timeOfSorting;

    public SortingList(){
        timeOfSorting = new ArrayList();
    }

    public void addTime(SortingTime sortingTime) {
        timeOfSorting.add(sortingTime);
    }

    public void setTimes(List<SortingTime> timeList) {
        this.timeOfSorting = timeList;
    }

    public List<SortingTime> getTimes() {
        return timeOfSorting;
    }

}
