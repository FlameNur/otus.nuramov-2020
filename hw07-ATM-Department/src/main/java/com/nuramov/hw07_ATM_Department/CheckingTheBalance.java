package com.nuramov.hw07_ATM_Department;

public class CheckingTheBalance implements DepartmentRequest {
     Atm atm;

    public CheckingTheBalance(Atm atm) {
        this.atm = atm;
    }

    @Override
    public void execute() {
        atm.checkingTheBalance();
    }
}
