package com.nuramov.hw07_ATM_Department;

import java.util.List;

    /*
    class SumOfAllBalances - выполняет запрос на возврат суммы остатков всех Atm
     */

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

    public int getSumOfAllAtmBalances() {
        return sumOfAllAtmBalances;
    }
}
