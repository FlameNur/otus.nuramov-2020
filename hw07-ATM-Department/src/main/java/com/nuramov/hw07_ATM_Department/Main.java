package com.nuramov.hw07_ATM_Department;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        VersionController versionController = new VersionController();
        Atm atm1 = new AtmExample(versionController);
        //Atm atm2 = new AtmExample();

        List<Atm> listOfAtms = new ArrayList<>();
        listOfAtms.add(atm1);
        //listOfAtms.add(atm2);


        // Проба пера
        WithdrawStrategy withdraw = new EffectiveWithdrawStrategy();

        atm1.depositMoney(Rub.RUB_50, 5);
        atm1.depositMoney(Rub.RUB_500, 2);
        atm1.depositMoney(Rub.RUB_100, 2);

        atm1.withdrawMoney(100, withdraw);

        System.out.println("После снятия 100 рублей:");
        atm1.getBanknoteCells();

        atm1.withdrawMoney(100, withdraw);

        System.out.println("После снятия еще 100 рублей:");
        atm1.getBanknoteCells();

        //----------------------------------------------------



        AtmDepartment atmDepartment = new AtmDepartment(
                new SumOfAllBalances(listOfAtms),
                new RollBackOfAtmState(listOfAtms)
        );

        atmDepartment.sumOfAllBalancesRequest();
        atmDepartment.rollBackOfAtmStateRequest();

        // Проба пера
        System.out.println("После возврата к прежней версии");
        atm1.getBanknoteCells();

    }
}
