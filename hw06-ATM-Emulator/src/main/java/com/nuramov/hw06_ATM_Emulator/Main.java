package com.nuramov.hw06_ATM_Emulator;

public class Main {
    public static void main(String[] args) {
        Atm myAtm = new Atm();

        Rub myRub1 = new Rub(500);
        myAtm.run(new DepositMoney(myRub1));   // Внесли 500 рублей
        myAtm.run(new AtmBalance());           // Проверили баланс банкомата

        Rub myRub2 = new Rub(150);
        myAtm.run(new WithdrawMoney(myRub2));  // Вывели 150 рублей
        myAtm.run(new AtmBalance());           // Проверили баланс банкомата
    }
}
