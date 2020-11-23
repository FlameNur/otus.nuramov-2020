package com.nuramov.hw06_ATM_Emulator;

import java.util.ArrayList;
import java.util.List;

public class Rub {

    // Номиналы банкнот
    public static final int rub_50 = 50;
    public static final int rub_100 = 100;
    public static final int rub_500 = 500;
    public static final int rub_1000 = 1000;
    public static final int rub_5000 = 5000;

    public static final List<Integer> rub_denomination = new ArrayList<>();

    static {
        rub_denomination.add(Rub.rub_5000);
        rub_denomination.add(Rub.rub_1000);
        rub_denomination.add(Rub.rub_500);
        rub_denomination.add(Rub.rub_100);
        rub_denomination.add(Rub.rub_50);
    }
}
