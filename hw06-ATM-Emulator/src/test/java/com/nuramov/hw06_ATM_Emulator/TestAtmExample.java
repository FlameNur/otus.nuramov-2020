package com.nuramov.hw06_ATM_Emulator;

import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestAtmExample {
    private AtmExample atmExample;
    private Map<Integer, Integer> banknoteCells;

    @BeforeAll
    void initAtm() {
        atmExample = new AtmExample();
    }

    @BeforeAll
    void initBanknoteCells() {
        banknoteCells = new TreeMap<>();
    }

    @BeforeAll
    void BanknoteCells() {
        banknoteCells.put(Rub.RUB_5000.getValue(), 0);
        banknoteCells.put(Rub.RUB_1000.getValue(), 0);
        banknoteCells.put(Rub.RUB_500.getValue(), 0);
        banknoteCells.put(Rub.RUB_100.getValue(), 0);
        banknoteCells.put(Rub.RUB_50.getValue(), 0);
    }

    @Test
    void testDepositMoney() {

    }

}
