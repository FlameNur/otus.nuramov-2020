package com.nuramov.hw07_ATM_Department.memento;

import com.nuramov.hw07_ATM_Department.memento.Save;

import java.util.Deque;
import java.util.LinkedList;

/** Структура class VersionController подчиняется паттерну Мементо (pattern Memento).
    class VersionController возвращает предыдущее и первичное состояние Atm */

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
