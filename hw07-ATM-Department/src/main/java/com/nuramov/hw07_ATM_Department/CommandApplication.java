package com.nuramov.hw07_ATM_Department;

import java.util.ArrayList;
import java.util.List;

public class CommandApplication {
    public static void main(String[] args) {
        Atm atm1 = new AtmExample();
        Atm atm2 = new AtmExample();

        List<Atm> listOfAtms = new ArrayList<>();
        listOfAtms.add(atm1);
        listOfAtms.add(atm2);

        AtmDepartment atmDepartment = new AtmDepartment(
                new SumOfAllBalances(listOfAtms),
                new RollBackOfAtmState(atm1)
        );

        atmDepartment.sumOfAllBalancesRequest();
        atmDepartment.rollBackOfAtmStateRequest();

    }
}
