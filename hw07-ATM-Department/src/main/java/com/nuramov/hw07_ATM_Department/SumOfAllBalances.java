package com.nuramov.hw07_ATM_Department;

import java.util.ArrayList;
import java.util.List;

public class SumOfAllBalances implements DepartmentRequest {
    int sumOfAllAtmBalances;
    List<Atm> listOfAtms;

    public SumOfAllBalances(List<Atm> listOfAtms) {
        this.listOfAtms = listOfAtms;
    }

    @Override
    public void execute() {
        for(Atm atm : listOfAtms) {
            sumOfAllAtmBalances += atm.getBalance();
        }
        System.out.println("Сумма всех остатков: " + sumOfAllAtmBalances);
    }
}
