package com.nuramov.hw07_ATM_Department;

import java.util.ArrayList;
import java.util.List;

    /*
       Архитектура приложения выполнена на основе двух паттернов - pattern Command и pattern Memento
       class AtmDepartment отправляет запросы:
            - sumOfAllBalancesRequest()             - запрос на возврат суммы остатков всех Atm;
            - rollbackToPreviousAtmStateRequest()   - запрос на возврат предыдущего состояния всех Atm;
            - rollbackToInitialAtmStateRequest()    - запрос на возврат первичного состояния всех Atm.
       interface DepartmentRequest обрабатывает запросы посредством следующих классов:
            - class SumOfAllBalances;
            - class RollbackToPreviousAtmState;
            - class RollbackToInitialAtmState.
       class AtmExample выполняет указанные запросы.
       class Save сохраняет состояние AtmExample (balance и banknoteCells).
       class VersionController возвращает предыдущее и первичное состояние AtmExample (balance и banknoteCells).
     */

public class Main {
    public static void main(String[] args) {
        VersionController versionController = new VersionController();
        WithdrawStrategy withdraw = new EffectiveWithdrawStrategy();
        Atm atm1 = new AtmExample(versionController);

        List<Atm> listOfAtms = new ArrayList<>();
        listOfAtms.add(atm1);

        atm1.depositMoney(Rub.RUB_50, 3);
        atm1.depositMoney(Rub.RUB_500, 2);
        atm1.depositMoney(Rub.RUB_100, 2);

        atm1.withdrawMoney(350, withdraw);

        AtmDepartment atmDepartment = new AtmDepartment(
                new SumOfAllBalances(listOfAtms),
                new RollbackToPreviousAtmState(listOfAtms),
                new RollbackToInitialAtmState(listOfAtms)
        );

        atmDepartment.sumOfAllBalancesRequest();
        atmDepartment.rollbackToPreviousAtmStateRequest();
        atmDepartment.rollbackToInitialAtmStateRequest();
    }
}
