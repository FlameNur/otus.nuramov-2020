package com.nuramov.hw07_ATM_Department;

public abstract class Atm {
    private Atm atm;

    public void setNextAtm (Atm atm) {
        this.atm = atm;
    }

    public void atmManager() {
        checkingTheBalance();
        if(atm != null) {
            atm.atmManager();
        }
    }

    // Выводим денежные средства из Atm. Количество денежных средств и стратегия выдачи банкнот
    public abstract void withdrawMoney();

    // Вводим денежные средства в Atm: банкнота и его количество
    public abstract void depositMoney();

    // Проверяем баланс Atm
    public abstract void atmBalance();

    // Проверка баланса через pattern Chain of Responsibility
    public abstract void checkingTheBalance();

    // Выводит баланс Atm
    // Проверка через pattern Command
    public abstract int getBalance();
}
