package com.nuramov.hw06_ATM_Emulator;

/*
Интерфейс определяет основные функции банкомата
 */

public interface Atm {

    void withdrawMoney(int money, WithdrawStrategy withdrawStrategy);

    void depositMoney(Rub rub, int moneyCount);

    void atmBalance();
}
