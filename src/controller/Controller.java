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

    public void updateView() {
        view.updateMainFrame();
    }

    public SortingList getModel() {
        return model;
    }

    public void generateMassive(int maxNumber, int step){
        GenerateMassive generateMassive = new GenerateMassive(maxNumber, step);
        List list = generateMassive.generate();
        changeData(list);
    }

    public void changeData(List<SortingTime> list) {
        model.setTimes(list);
        //updateView();
    }
}
