package com.nuramov.hw07_ATM_Department;

import com.nuramov.hw07_ATM_Department.Atm.Atm;
import com.nuramov.hw07_ATM_Department.Atm.AtmDepartment;
import com.nuramov.hw07_ATM_Department.Atm.AtmExample;
import com.nuramov.hw07_ATM_Department.DepartmentRequests.DepartmentRequest;
import com.nuramov.hw07_ATM_Department.DepartmentRequests.RollbackToInitialAtmState;
import com.nuramov.hw07_ATM_Department.DepartmentRequests.RollbackToPreviousAtmState;
import com.nuramov.hw07_ATM_Department.MonetaryCurrency.Rub;
import com.nuramov.hw07_ATM_Department.WithdrawStrategies.EffectiveWithdrawStrategy;
import com.nuramov.hw07_ATM_Department.WithdrawStrategies.WithdrawStrategy;
import com.nuramov.hw07_ATM_Department.WorkingWithSavedAtmStates.VersionController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Тест проводит проверку метода sumOfAllBalances класса AtmDepartment */

class AtmDepartmentTest {
    private static AtmDepartment atmDepartment;
    private static DepartmentRequest rollbackToPreviousAtmState;
    private static DepartmentRequest rollbackToInitialAtmState;
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

    @BeforeAll
    static void initDepartmentRequest() {
        rollbackToPreviousAtmState = new RollbackToPreviousAtmState(listOfAtms);
        rollbackToInitialAtmState = new RollbackToInitialAtmState(listOfAtms);
    }

    @BeforeEach
    void fillTheListOfAtms() {
        listOfAtms.add(atmExample1);
        listOfAtms.add(atmExample2);
    }

    @BeforeEach
    void initAtmDepartment() {
        atmDepartment = new AtmDepartment(rollbackToPreviousAtmState, rollbackToInitialAtmState);
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

        int sum = atmDepartment.sumOfAllBalances(listOfAtms);

        assertEquals(700, sum);
    }

    @AfterEach
    void TestEnd() {
        System.out.println("Тест успешно завершен");
    }


}