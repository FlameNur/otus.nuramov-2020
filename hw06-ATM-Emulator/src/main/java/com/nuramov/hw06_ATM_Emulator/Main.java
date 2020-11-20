package com.nuramov.hw06_ATM_Emulator;

public class Main {
    public static void main(String[] args) {
        AtmExample atm1 = new AtmExample();

        Rub rub1 = new Rub(10000);
        Rub rub2 = new Rub(7650);
        atm1.depositMoney(rub1);
        atm1.withdrawMoney(rub2);
        atm1.withdrawMoney(rub2);
        atm1.atmBalance();

        Rub rub3 = new Rub(23);
        Rub rub4 = new Rub(501);
        Rub rub5 = new Rub(0);
    }
}
