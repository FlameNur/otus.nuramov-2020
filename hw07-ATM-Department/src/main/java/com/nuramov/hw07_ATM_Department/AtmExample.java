package com.nuramov.hw07_ATM_Department;

public class AtmExample extends Atm {
    private int balance;
    private boolean stateOFAtm = true;

    @Override
    public void withdrawMoney() {

    }

    @Override
    public void depositMoney() {

    }

    @Override
    public void atmBalance() {

    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void setStateOFAtm(boolean stateOFAtm) {
        this.stateOFAtm = stateOFAtm;
    }
}
