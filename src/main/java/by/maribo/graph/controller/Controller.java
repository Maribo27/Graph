package by.maribo.graph.controller;

import by.maribo.graph.view.*;
import by.maribo.graph.model.*;

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

    void changeButtonState(){
        view.buttonHide();
    }

    void changeData(List<SortingTime> list) {
        model.setTimes(list);
    }

    public int getTimeZoom(){
        List<SortingTime> time = model.getTimes();
        int maxTime = 0;
        for (SortingTime iterator: time) {
            if (maxTime < iterator.getTime()) maxTime = iterator.getTime();
        }
        return maxTime;
    }

    public void changeSize(int width, int height, int scaleState, int scaleXState){
        view.changeSize(width, height, scaleState, scaleXState);
    }

}
