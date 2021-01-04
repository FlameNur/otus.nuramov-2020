package com.nuramov.hw07_ATM_Department;

public class CommandApplication {
    public static void main(String[] args) {
        Atm atm = new AtmExample1();

        AtmDepartment atmDepartment = new AtmDepartment(new CheckingTheBalance(atm));
        atmDepartment.CheckingTheBalanceRequest();
    }
}
