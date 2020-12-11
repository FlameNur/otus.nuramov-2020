package com.nuramov.hw06_ATM_Emulator;

public class Main {
    public static void main(String[] args) {
        AtmExample atm1 = new AtmExample();
        atm1.depositMoney(Rub.RUB_50, 5);
        atm1.depositMoney(Rub.RUB_500, 2);
        atm1.depositMoney(Rub.RUB_100, 2);

        atm1.atmBalance();
        atm1.banknoteCells();

        atm1.withdrawMoney(750);

        atm1.atmBalance();
        atm1.banknoteCells();

    }
}
