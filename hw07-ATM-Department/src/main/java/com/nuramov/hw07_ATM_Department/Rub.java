package com.nuramov.hw07_ATM_Department;

    /** enum Rub представляет собой номиналы банкнот российского рубля.
    Каждая банкнота имеет соответсвуюший вес. */

public enum Rub {

    RUB_50(50),
    RUB_100(100),
    RUB_500(500),
    RUB_1000(1000),
    RUB_5000(5000);

    private int value;

    Rub(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
