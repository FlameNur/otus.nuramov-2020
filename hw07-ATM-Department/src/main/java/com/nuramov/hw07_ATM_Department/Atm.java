package com.nuramov.hw07_ATM_Department;

public interface Atm {

    // Выводим денежные средства из Atm. Количество денежных средств и стратегия выдачи банкнот
    void withdrawMoney(int money, WithdrawStrategy withdrawStrategy);

    // Вводим денежные средства в Atm: банкнота и его количество
    void depositMoney(Rub rub, int moneyCount);

    // Проверяем баланс Atm
    void atmBalance();

    // Выводит баланс Atm
    int getBalance();

    void setStateOFAtm(boolean stateOFAtm);

    VersionController getVersionController();

    void load(Save save);
}
