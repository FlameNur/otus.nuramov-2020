package com.nuramov.hw07_ATM_Department;

import java.util.ArrayList;
import java.util.List;

    /*

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
