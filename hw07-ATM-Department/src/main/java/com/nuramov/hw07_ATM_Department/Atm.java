package com.nuramov.hw07_ATM_Department;

public abstract class Atm {
    private Atm next;

    public Atm linkWith(Atm next) {
        this.next = next;
        return  next;
    }

    public abstract boolean check();

    protected boolean checkNext() {
        if (next == null) {
            return true;
        }
        return next.check();
    }

    // Выводим денежные средства из Atm. Количество денежных средств и стратегия выдачи банкнот
    public abstract void withdrawMoney();

    // Вводим денежные средства в Atm: банкнота и его количество
    public abstract void depositMoney();

    // Проверяем баланс Atm
    public abstract void atmBalance();
}
