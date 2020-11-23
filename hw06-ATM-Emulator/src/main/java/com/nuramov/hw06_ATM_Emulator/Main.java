package com.nuramov.hw06_ATM_Emulator;

public class Main {
    public static void main(String[] args) {
        AtmExample atm1 = new AtmExample();
        atm1.depositMoney(100);
        atm1.depositMoney(0);
        atm1.depositMoney(103);
        atm1.depositMoney(10000);
        atm1.depositMoney(500);
        atm1.depositMoney(500);
        atm1.depositMoney(100);
        atm1.depositMoney(50);

        atm1.atmBalance();
        atm1.banknoteCells();

        atm1.withdrawMoney(750);

        atm1.atmBalance();
        atm1.banknoteCells();

    }
}
