package com.pencil.engine.geometry.selection;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiSelection {

    private ArrayList<Selection> selections;

    public MultiSelection() {
        selections = new ArrayList<>();
    }

    public MultiSelection(ArrayList<Selection> selections) {
        this.selections = selections;
    }

    public MultiSelection(Selection... selections) {
        this.selections = new ArrayList<Selection>();
        this.selections.addAll(Arrays.asList(selections));
    }

    public ArrayList<Selection> getSelections() {
        return selections;
    }
}
