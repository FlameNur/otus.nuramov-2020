package com.nuramov.hw07_ATM_Department.Atm;

import com.nuramov.hw07_ATM_Department.DepartmentRequests.DepartmentRequest;

import java.util.List;

/** Структура class AtmDepartment подчиняется паттерну Команда (pattern Command).
    AtmDepartment отправляет запросы, обрабатываемые интерфейсом DepartmentRequest
    (классы RollbackToPreviousAtmState, RollbackToInitialAtmState).
    Запросы выполняются над всеми Atm.

    Департамент Atm отправляет следующие запросы:
    - возврат всех Atm к предыдущему состоянию (класс RollbackToPreviousAtmState);
    - возврат всех Atm к начальному состоянию (класс RollbackToInitialAtmState).

    Метод sumOfAllBalances позволяет вернуть сумму остатков всех Atm */

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

    /** Возвращает сумму остатков всех Atm
     * @param listOfAtms - список всех Atm
     * @return Сумму остатков всех Atm
     */
    public int sumOfAllBalances(List<Atm> listOfAtms) {
        int sumOfAllAtmBalances = 0;
        for(Atm atm : listOfAtms) {
            sumOfAllAtmBalances += atm.getBalance();
        }
        System.out.println("Сумма всех остатков: " + sumOfAllAtmBalances);
        return sumOfAllAtmBalances;
    }
}
