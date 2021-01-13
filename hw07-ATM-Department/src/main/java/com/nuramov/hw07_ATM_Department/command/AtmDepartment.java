package com.nuramov.hw07_ATM_Department.command;

import com.nuramov.hw07_ATM_Department.Atm;

import java.util.List;

/** Структура class AtmDepartment подчиняется паттерну Команда (pattern Command).
    AtmDepartment отправляет запросы, обрабатываемые интерфейсом DepartmentRequest (классы SumOfAllBalances,
    RollbackToPreviousAtmState, RollbackToInitialAtmState).
    Запросы выполняются над всеми Atm.

    Департамент Atm отправляет следующие запросы:
    - возврат суммы всех остатков со всех Atm (класс SumOfAllBalances);
    - возврат всех Atm к предыдущему состоянию (класс RollbackToPreviousAtmState);
    - возврат всех Atm к начальному состоянию (класс RollbackToInitialAtmState). */

public class AtmDepartment {
    private DepartmentRequest rollbackToPreviousAtmState;
    private DepartmentRequest rollbackToInitialAtmState;

    public AtmDepartment(DepartmentRequest rollbackToPreviousAtmState,
                         DepartmentRequest rollbackToInitialAtmState) {
        this.rollbackToPreviousAtmState = rollbackToPreviousAtmState;
        this.rollbackToInitialAtmState = rollbackToInitialAtmState;
    }

    /** Запрос на возврат всех Atm к предыдущему состоянию */
    public void rollbackToPreviousAtmStateRequest() {
        rollbackToPreviousAtmState.execute();
    }

    /** Запрос на возврат всех Atm к начальному состоянию */
    public void rollbackToInitialAtmStateRequest() {
        rollbackToInitialAtmState.execute();
    }

    public int sumOfAllBalances(List<Atm> listOfAtms) {
        int sumOfAllAtmBalances = 0;
        for(Atm atm : listOfAtms) {
            sumOfAllAtmBalances += atm.getBalance();
        }
        System.out.println("Сумма всех остатков: " + sumOfAllAtmBalances);
        return sumOfAllAtmBalances;
    }
}
