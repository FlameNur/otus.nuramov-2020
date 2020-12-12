package com.nuramov.hw06_ATM_Emulator;

import java.util.*;

public class AtmExample implements Atm {
    private int balance;

    // Банкноты в Atm
    private Map<Integer, Integer> banknoteCells = new TreeMap<>();

    {
        banknoteCells.put(Rub.RUB_5000.getValue(), 0);
        banknoteCells.put(Rub.RUB_1000.getValue(), 0);
        banknoteCells.put(Rub.RUB_500.getValue(), 0);
        banknoteCells.put(Rub.RUB_100.getValue(), 0);
        banknoteCells.put(Rub.RUB_50.getValue(), 0);
    }

    // Выводим денежные средства из Atm.
    @Override
    public void withdrawMoney(int money, WithdrawStrategy withdrawStrategy) {
        Map<Integer, Integer> localBanknoteCells = banknoteCells;

        // Выдаваемые банкноты
        Map<Integer, Integer> paymentCells = new TreeMap<>();

        // Проверка на достаточное количество средств на счете Atm
        if(checkBalance(money)) {
            System.out.println("Недостаточно средств на счете");
            return;
        }

        // Проверка на достаточное количество банкнота для выдачи на счете Atm
        if(checkBanknoteCells(money)) {
            System.out.println("Недостаточно средств на счете");
            return;
        }

        balance -= money;
        System.out.println("Вы сняли: " + money + " рублей ");

        paymentCells = withdrawStrategy.payment(money, paymentCells, localBanknoteCells);
        System.out.println("Количество выданных банкнот: ");
        for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
            System.out.println(m.getKey() + " - x" + m.getValue());
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
    // Если денег недостаточно, возвращаем true и завершаем операцию
    private boolean checkBalance(int money) {
        boolean b = false;
        if(balance - money < 0) {
            b = true;
        }
        return b;
    }

    // Проверяем количество банкнот для выдачи
    // Если банкнот недостаточно, возвращаем true и завершаем операцию
    private boolean checkBanknoteCells(int money) {
        boolean b = false;
        if(balance - money < 0) {
            b = true;
        }
        return b;
    }
}
