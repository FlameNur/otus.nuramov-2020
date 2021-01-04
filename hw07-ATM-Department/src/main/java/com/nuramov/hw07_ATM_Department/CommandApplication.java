package com.nuramov.hw07_ATM_Department;

import java.util.ArrayList;
import java.util.List;

public class CommandApplication {
    public static void main(String[] args) {
        Atm atm1 = new AtmExample1();
        Atm atm2 = new AtmExample2();
        Atm atm3 = new AtmExample3();

        List<Atm> listOfAtms = new ArrayList<>();
        listOfAtms.add(atm1);
        listOfAtms.add(atm2);
        listOfAtms.add(atm3);

        // Проверка работы pattern Command
        AtmDepartment atmDepartment = new AtmDepartment(new SumOfAllBalances(listOfAtms));
        atmDepartment.sumOfAllBalancesRequest();

        System.out.println("Chain of Responsibility:");

        // Проверка работы pattern Chain of Responsibility
        atm1.setNextAtm(atm2);
        atm2.setNextAtm(atm3);

        atm1.atmManager();

    }
}
