package com.nuramov.hw07_ATM_Department;

import com.nuramov.hw07_ATM_Department.command.RollbackToPreviousAtmState;
import com.nuramov.hw07_ATM_Department.memento.VersionController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    /** Тест проводит проверку метода execute класса RollbackToPreviousAtmState */

class RollbackToPreviousAtmStateTest {
    private static RollbackToPreviousAtmState rollbackToPreviousAtmState;
    private static AtmExample atmExample1;
    private static AtmExample atmExample2;
    private static WithdrawStrategy withdrawStrategy;
    private static VersionController versionController1;
    private static VersionController versionController2;
    private static List<Atm> listOfAtms;

    @BeforeAll
    static void initVersionController() {
        versionController1 = new VersionController();
        versionController2 = new VersionController();
    }

    @BeforeAll
    static void initStrategy() {
        withdrawStrategy = new EffectiveWithdrawStrategy();
    }

    @BeforeAll
    static void initAtm() {
        atmExample1 = new AtmExample(versionController1);
        atmExample2 = new AtmExample(versionController2);
    }

    @BeforeAll
    static void initListOfAtms() {
        listOfAtms = new ArrayList<>();
    }

    @BeforeEach
    void initRollbackToInitialAtmState() {
        listOfAtms.add(atmExample1);
        listOfAtms.add(atmExample2);
        rollbackToPreviousAtmState = new RollbackToPreviousAtmState(listOfAtms);
    }

    @BeforeEach
    void TestStart() {
        System.out.println("\n" + "Начало Теста:");
    }

    @Test
    void executeTest() {
        atmExample1.depositMoney(Rub.RUB_50, 7);
        atmExample1.withdrawMoney(150, withdrawStrategy);
        atmExample2.depositMoney(Rub.RUB_500, 5);
        atmExample2.withdrawMoney(2000, withdrawStrategy);

        rollbackToPreviousAtmState.execute();

        assertEquals(350, atmExample1.getBalance());
        assertEquals(2500, atmExample2.getBalance());
    }

    @AfterEach
    void TestEnd() {
        System.out.println("Тест успешно завершен");
    }
}