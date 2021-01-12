package com.nuramov.hw07_ATM_Department;

import com.nuramov.hw07_ATM_Department.command.SumOfAllBalances;
import com.nuramov.hw07_ATM_Department.memento.VersionController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    /*
    Тест проводит проверку метода execute класса SumOfAllBalances
     */

class SumOfAllBalancesTest {
    private static SumOfAllBalances sumOfAllBalances;
    private static AtmExample atmExample;
    private static WithdrawStrategy withdrawStrategy;
    private static VersionController versionController;
    private static List<Atm> listOfAtms;

    @BeforeAll
    static void initVersionController() {
        versionController = new VersionController();
    }

    @BeforeAll
    static void initStrategy() {
        withdrawStrategy = new EffectiveWithdrawStrategy();
    }

    @BeforeAll
    static void initAtm() {
        atmExample = new AtmExample(versionController);
    }

    @BeforeAll
    static void initListOfAtms() {
        listOfAtms = new ArrayList<>();
    }

    @BeforeEach
    void initRollbackToInitialAtmState() {
        listOfAtms.add(atmExample);
        sumOfAllBalances = new SumOfAllBalances(listOfAtms);
    }

    @BeforeEach
    void TestStart() {
        System.out.println("\n" + "Начало Теста:");
    }

    @Test
    void executeTest() {
        atmExample.depositMoney(Rub.RUB_50, 7);
        atmExample.withdrawMoney(150, withdrawStrategy);
        sumOfAllBalances.execute();
        assertEquals(200, sumOfAllBalances.getSumOfAllAtmBalances());
    }

    @AfterEach
    void TestEnd() {
        System.out.println("Тест успешно завершен");
    }

}