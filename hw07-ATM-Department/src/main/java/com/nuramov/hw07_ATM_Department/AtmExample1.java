package com.nuramov.hw07_ATM_Department;

public class AtmExample1 extends Atm {
    private int balance = 1000;

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
    public void checkingTheBalance() {
        System.out.println("Баланс: " + getBalance());
    }

    public int getBalance() {
        return balance;
    }
}