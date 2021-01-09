package com.nuramov.hw07_ATM_Department;

import java.util.Deque;
import java.util.LinkedList;

public class VersionController {
    private final Deque<Save> history = new LinkedList<>();

    public Save getSave() {
        return  history.getLast();
    }

    public void setSave(Save save) {
        this.history.add(save);
    }

    public Save getInitialSave() {
        return history.getFirst();
    }
}
