package com.nuramov.hw06_ATM_Emulator;

/*
Интерфейс определяет основные функции банкомата
 */

public interface Atm {

    // Выводим денежные средства из Atm. Количество денежных средств и стратегия выдачи банкнот
    void withdrawMoney(int money, WithdrawStrategy withdrawStrategy);

    // Вводим денежные средства в Atm: банкнота и его количество
    void depositMoney(Rub rub, int moneyCount);

    // Проверяем баланс Atm
    void atmBalance();
}
