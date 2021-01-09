package com.nuramov.hw07_ATM_Department;

import java.util.LinkedList;
import java.util.Map;

public class Save {
    private final Map<Integer, Integer> savedBanknoteCells;

    public Save(Map<Integer, Integer> savedBanknoteCells) {
        this.savedBanknoteCells = savedBanknoteCells;
    }

    public Map<Integer, Integer> getSavedBanknoteCells() {
        return savedBanknoteCells;
    }
}
