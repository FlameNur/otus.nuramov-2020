package com.nuramov.hw06_ATM_Emulator;

import java.util.*;
import java.util.stream.Collectors;

public class AtmExample implements Atm {
    private int balance;
    private Map<Integer, Integer> banknoteCells = new TreeMap<>();

    {
        banknoteCells.put(Rub.rub_5000, 0);
        banknoteCells.put(Rub.rub_1000, 0);
        banknoteCells.put(Rub.rub_500, 0);
        banknoteCells.put(Rub.rub_100, 0);
        banknoteCells.put(Rub.rub_50, 0);
    }

    // Выводим денежные средства из Atm.
    @Override
    public void withdrawMoney(int money) {
        // Выдаваемые банкноты
        Map<Integer, Integer> paymentCells = new TreeMap<>();

        // Проверка на достаточное количество средств на счете Atm
        if(checkBalance(money)) {
            System.out.println("Недостаточно средств на счете");
            return;
        }
        balance -= money;
        System.out.println("Вы сняли: " + money + " рублей ");

        paymentCells = payment(money, paymentCells);
        System.out.println("Количество выданных банкнот: ");
        for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
            System.out.println(m.getKey() + " - x" + m.getValue());
        }
    }

    // Вводим денежные средства в Atm. По одной банкноте за один раз
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

    // Определяем количество банкнот для выдачи.
    // Возвращаем Map paymentCells с требуемым количеством банкнот
    private Map<Integer, Integer> payment(int money, Map<Integer, Integer> paymentCells) {
        int localMoney = money;

        // Реверсивная сортировка Map banknoteCells (от 5000 до 50)
        Map<Integer, Integer> treeMap = new TreeMap<>(Comparator.reverseOrder());
        treeMap.putAll(banknoteCells);

        // В цикле определяем:
        // 1 - наличие банкноты (условие);
        // 2 - количество конкретной банкноты для погашения части/всей суммы (переменная - n);
        // 3 - проверяем корректность значения переменной - n исходя из имеющегося значения m.getValue() (условие)
        // 4 - уменьшаем общую сумму исходя из значения переменной - n и номинала банкноты
        // 5, 6 - добавляем соответствующую банкноту в Map paymentCells и удаляем ее количество из Map banknoteCells
        // 7 - если для погашения суммы требуется большее количество банкнот, чем имеется,
        // погашаем чем имеем. Повторяем пп. 5 и 6
        for (Map.Entry<Integer, Integer> m : treeMap.entrySet()) {
            if(m.getValue() > 0) {
                int n = localMoney / m.getKey();
                if(n != 0 & (n <= m.getValue())) {
                    localMoney = localMoney - (n * m.getKey());
                    paymentCells.put(m.getKey(), n);
                    banknoteCells.put(m.getKey(), m.getValue() - n);
                } else if(n > m.getValue()) {
                    localMoney = localMoney - (m.getValue() * m.getKey());
                    paymentCells.put(m.getKey(), m.getValue());
                    banknoteCells.put(m.getKey(), 0);
                }
            }
        }
        return paymentCells;
    }
}
