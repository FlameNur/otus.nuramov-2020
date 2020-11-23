package com.nuramov.hw06_ATM_Emulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtmExample implements Atm {
    private int balance;
    private Map<Integer, Integer> banknoteCells = new HashMap<>();

    {
        banknoteCells.put(Rub.rub_50, 0);
        banknoteCells.put(Rub.rub_100, 0);
        banknoteCells.put(Rub.rub_500, 0);
        banknoteCells.put(Rub.rub_1000, 0);
        banknoteCells.put(Rub.rub_5000, 0);
    }

    @Override
    public void withdrawMoney(int money) {
        // Проверка на достаточное количество средств на счете Atm
        if(checkBalance(money)) {
            System.out.println("Недостаточно средств на счете");
            return;
        }

        balance -= money;
        System.out.println("Вы сняли: " + money + " рублей ");
    }

    @Override
    public void depositMoney(int money) {
        // Проверка правильности ввода
        if(inputValidation(money)) {
            System.out.println("Введите правильное значение");
            return;
        }

        System.out.println("Вы внесли: " + money + " рублей ");
        balance += money;

        // Увеличиваем количество банкноты
        int count = banknoteCells.get(money) + 1;
        banknoteCells.put(money, count);
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
    // Если денег недостаточно, возвращаем true и с ошибкой завершаем операцию
    private boolean checkBalance(int money) {
        boolean b = false;
        if(balance - money < 0) {
            b = true;
        }
        return b;
    }

    // Проверяем на соответствие используемых банкнот и введенного значения.
    // Если значение money не соответсвует номиналу банкноты, возвращаем true и с ошибкой завершаем операцию
    private boolean inputValidation(int money) {
        boolean b = true;
        for(Integer i : Rub.rub_denomination) {
            if(money == i) {
                b = false;
                break;
            }
        }
        return b;
    }
}
