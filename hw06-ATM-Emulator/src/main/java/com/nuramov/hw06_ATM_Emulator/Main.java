package com.nuramov.hw06_ATM_Emulator;

public class Main {
    public static void main(String[] args) {
        Atm myAtm = new Atm();

        Rub myRub1 = new Rub(10000);
        myAtm.run(new DepositMoney(myRub1));   // Внесли в Atm

        Rub myRub2 = new Rub(7650);
        myAtm.run(new WithdrawMoney(myRub2));  // Вывели из Atm

        myAtm.run(new AtmBalance());           // Вывели баланс Atm
    }
}
