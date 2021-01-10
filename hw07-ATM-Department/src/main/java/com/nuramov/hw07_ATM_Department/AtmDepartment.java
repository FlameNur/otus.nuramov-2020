package com.nuramov.hw07_ATM_Department;

    /*
    Структура класса AtmDepartment подчиняется паттерну Команда (pattern Command).
    AtmDepartment отправляет запросы, обрабатываемые интерфейсом DepartmentRequest (классы SumOfAllBalances,
    RollbackToPreviousAtmState, RollbackToInitialAtmState).
    Запросы выполняются над всеми Atm.

    Департамент Atm отправляет следующие запросы:
    - возврат суммы всех остатков со всех Atm (класс SumOfAllBalances);
    - возврат всех Atm к предыдущему состоянию (класс RollbackToPreviousAtmState);
    - возврат всех Atm к начальному состоянию (класс RollbackToInitialAtmState).
     */

public class AtmDepartment {
    DepartmentRequest sumOfAllBalances;
    DepartmentRequest rollbackToPreviousAtmState;
    DepartmentRequest rollbackToInitialAtmState;

    public AtmDepartment(DepartmentRequest sumOfAllBalances,
                         DepartmentRequest rollbackToPreviousAtmState,
                         DepartmentRequest rollbackToInitialAtmState) {
        this.sumOfAllBalances = sumOfAllBalances;
        this.rollbackToPreviousAtmState = rollbackToPreviousAtmState;
        this.rollbackToInitialAtmState = rollbackToInitialAtmState;
    }

    // Запрос на возврат суммы всех остатков со всех Atm
    public void sumOfAllBalancesRequest() {
        sumOfAllBalances.execute();
    }

    // Запрос на возврат всех Atm к предыдущему состоянию
    public void rollbackToPreviousAtmStateRequest() {
        rollbackToPreviousAtmState.execute();
    }

    // Запрос на возврат всех Atm к начальному состоянию
    public void rollbackToInitialAtmStateRequest() {
        rollbackToInitialAtmState.execute();
    }
}
