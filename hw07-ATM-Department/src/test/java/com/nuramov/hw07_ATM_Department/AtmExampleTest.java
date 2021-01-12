package com.nuramov.hw07_ATM_Department;

import com.nuramov.hw07_ATM_Department.memento.VersionController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

    /*
    Тест проводит проверку методов withdrawMoney и depositMoney класса AtmExample
     */

class AtmExampleTest {
    private static AtmExample atmExample;
    private static WithdrawStrategy withdrawStrategy;
    private static VersionController versionController;

    @BeforeAll
    static void initVersionController() {
        versionController = new VersionController();
    }

    @BeforeAll
    static void initStrategy() {
        withdrawStrategy = new EffectiveWithdrawStrategy();
    }

    @BeforeEach
    void initAtm() {
        atmExample = new AtmExample(versionController);
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

    @Test
    void loadTest() {
        atmExample.depositMoney(Rub.RUB_50, 7);
        atmExample.withdrawMoney(150, withdrawStrategy);
        System.out.println("Загружаем предыдущее состояние Atm...");
        atmExample.load(atmExample.getVersionController().getSave());
        assertEquals(350, atmExample.getBalance());
    }

    @AfterEach
    void TestEnd() {
        System.out.println("\n" + "Баланс Atm: " + atmExample.getBalance());
        System.out.println("Тест успешно завершен");
    }
}