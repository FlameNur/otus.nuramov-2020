package com.nuramov.hw06_ATM_Emulator;

public interface Atm {

    void withdrawMoney(int money);

    void depositMoney(Rub rub, int moneyCount);

    void atmBalance();


}
