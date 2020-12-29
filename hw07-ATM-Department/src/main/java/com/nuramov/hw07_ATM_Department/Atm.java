package com.nuramov.hw07_ATM_Department;

/*
Интерфейс определяет основные функции банкомата
 */

public interface Atm {

    // Выводим денежные средства из Atm. Количество денежных средств и стратегия выдачи банкнот
    void withdrawMoney();

    // Вводим денежные средства в Atm: банкнота и его количество
    void depositMoney();

    // Проверяем баланс Atm
    void atmBalance();
}
