package controller;

import view.*;
import model.*;

import java.util.List;

/**
 * Created by Maria on 27.04.2017.
 */
public class Controller {
    private Interface view;
    private SortingList model = new SortingList();

    public void runProgram(){
        view = new Interface(this);
        view.runProgram();
    }

    void updateView() {
        view.updateMainFrame();
    }

    public SortingList getModel() {
        return model;
    }

    public void generateMassive(int maxNumber, int step){
        Thread generateMassive = new GenerateMassive(maxNumber, step, this);
        generateMassive.start();
    }

    void changeData(List<SortingTime> list) {
        model.setTimes(list);
    }

    public int getTimeZoom(){
        List<SortingTime> time = model.getTimes();
        int max = 0;
        for (SortingTime t: time) {
            if (max < t.getTime()) max = t.getTime();
        }
        if (max % 10 != 0){
            max += 10;
            max = max / 10;
            max = max * 10;
        }
        return max;
    }

}
