package com.nuramov.hw07_ATM_Department.WorkingWithSavedAtmStates;

import java.util.Map;

/** Структура class Save подчиняется паттерну Мементо (pattern Memento).
    class Save сохраняет состояние Atm (balance и banknoteCells) */

public class Save {
    private final Map<Integer, Integer> savedBanknoteCells;
    private final int savedBalance;

    public Save(Map<Integer, Integer> savedBanknoteCells, int savedBalance) {
        this.savedBanknoteCells = savedBanknoteCells;
        this.savedBalance = savedBalance;
    }

    public Map<Integer, Integer> getSavedBanknoteCells() {
        return savedBanknoteCells;
    }

    public int getSavedBalance() {
        return savedBalance;
    }
}
