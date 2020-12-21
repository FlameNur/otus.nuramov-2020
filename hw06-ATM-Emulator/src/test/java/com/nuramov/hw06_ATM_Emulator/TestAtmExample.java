package com.nuramov.hw06_ATM_Emulator;

import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

    /*
    Тест проводит проверку методов withdrawMoney и depositMoney класса AtmExample
     */

public class TestAtmExample {
    private static AtmExample atmExample;
    private static WithdrawStrategy withdrawStrategy;

    @BeforeAll
    static void initStrategy() {
        withdrawStrategy = new EffectiveWithdrawStrategy();
    }

    @BeforeEach
    void initAtm() {
        atmExample = new AtmExample();
    }

    @BeforeEach
    void TestStart() {
        System.out.println("\n" + "Начало Теста:");
    }

    @Test
    void depositMoneyTest() {
        atmExample.depositMoney(Rub.RUB_500, 3);
        assertEquals(1500, atmExample.getBalance());
    }

    @Test
    void withdrawMoneyTest() {
        atmExample.depositMoney(Rub.RUB_50, 7);
        atmExample.withdrawMoney(150, withdrawStrategy);
        assertEquals(200, atmExample.getBalance());
    }

    @AfterEach
    void TestEnd() {
        System.out.println("\n" + "Баланс Atm: " + atmExample.getBalance());
        System.out.println("Тест успешно завершен");
    }
}
