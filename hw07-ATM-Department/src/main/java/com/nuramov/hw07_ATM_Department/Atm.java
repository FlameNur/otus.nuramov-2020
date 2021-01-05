package com.nuramov.hw07_ATM_Department;

public abstract class Atm {

    // Выводим денежные средства из Atm. Количество денежных средств и стратегия выдачи банкнот
    public abstract void withdrawMoney();

    // Вводим денежные средства в Atm: банкнота и его количество
    public abstract void depositMoney();

    // Проверяем баланс Atm
    public abstract void atmBalance();

    // Выводит баланс Atm
    public abstract int getBalance();
}
