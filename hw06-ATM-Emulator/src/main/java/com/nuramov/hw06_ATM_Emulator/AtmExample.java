package com.nuramov.hw06_ATM_Emulator;

import java.util.*;

public class AtmExample implements Atm {
    private int balance;

    // Банкноты в Atm
    private final Map<Integer, Integer> banknoteCells = new TreeMap<>();

    {
        banknoteCells.put(Rub.RUB_5000.getValue(), 0);
        banknoteCells.put(Rub.RUB_1000.getValue(), 0);
        banknoteCells.put(Rub.RUB_500.getValue(), 0);
        banknoteCells.put(Rub.RUB_100.getValue(), 0);
        banknoteCells.put(Rub.RUB_50.getValue(), 0);
    }

    // Выводим денежные средства из Atm. Количество денежных средств и стратегия выдачи банкнот
    @Override
    public void withdrawMoney(int money, WithdrawStrategy withdrawStrategy) {
        // Выдаваемые банкноты
        Map<Integer, Integer> paymentCells = new TreeMap<>();

        // Проверка на достаточное количество средств на счете Atm
        // Если денег недостаточно, возвращаем false и завершаем операцию
        if(!checkBalance(money)) {
            System.out.println("Недостаточно средств на счете");
            return;
        }

        // Возвращаем true, если все условия для выдачи денежных средств выполняются
        // Определяем количество выдаваемых банкнот
        // Количество банкнот может меняться в заисимости от выбранной стратегии в классе Main
        boolean payment = withdrawStrategy.payment(money, paymentCells, banknoteCells);

        if(payment) {
            balance -= money;
        }
    }

    // Вводим денежные средства в Atm: банкнота и его количество
    @Override
    public void depositMoney(Rub rub, int rubCount) {
        // Количество внесенных денежных средств
        int money = rub.getValue() * rubCount;
        System.out.println("Вы внесли: " + money + " рублей ");
        balance += money;

        // Увеличиваем количество банкноты
        int count = banknoteCells.get(rub.getValue()) + rubCount;
        banknoteCells.put(rub.getValue(), count);
    }

    // Проверяем баланс Atm
    @Override
    public void atmBalance() {
        System.out.println("Баланс Atm: " + balance + " рублей");
    }

    // Выводим количество имеющихся банкнот в Atm
    public void banknoteCells() {
        System.out.println("Количество банкнот в Atm: ");
        for (Map.Entry<Integer, Integer> m : banknoteCells.entrySet()) {
            System.out.println(m.getKey() + " - x" + m.getValue());
        }
    }

    // Проверяем баланс Atm.
    // Если денег недостаточно, возвращаем false
    private boolean checkBalance(int money) {
        boolean b = true;
        if(balance - money < 0) {
            b = false;
        }
        return b;
    }
}
