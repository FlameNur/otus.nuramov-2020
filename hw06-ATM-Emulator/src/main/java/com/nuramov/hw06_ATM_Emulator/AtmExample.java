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
        if(checkBalance(money)) {
            System.out.println("Недостаточно средств на счете");
            return;
        }

        // Определяем количество выдаваемых банкнот
        // Количество банкнот может меняться в заисимости от выбранной стратегии в классе Main
        paymentCells = withdrawStrategy.payment(money, paymentCells, banknoteCells);

        int localMoney = 0;

        // Определяем сумму (localMoney) всех банкнот для последующей проверки случая,
        // когда запрошенная сумма для выдачи меньше баланса (balance),
        // но для ее выдачи не хватает требуемых банкнот
        for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
            localMoney += m.getKey() * m.getValue();
        }

        // Если запрошенное количество денежных средств удовлетворяет требованиям,
        // т.е. если money <= balance и количество банкнот в banknoteCells (localMoney == money) хватает для выдачи,
        // то выдается запрошенная сумма денежных средств
        if(localMoney == money) {
            balance -= money;
            System.out.println("Вы сняли: " + money + " рублей ");
            System.out.println("Количество выданных банкнот: ");
            for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
                System.out.println(m.getKey() + " - x" + m.getValue());
            }
        } else {
            for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
                // Банкноты сначала снимаются из banknoteCells, а если их недостаточно для выдачи,
                // обратно возвращаются в banknoteCells
                banknoteCells.put(m.getKey(), m.getValue());
            }
            System.out.println("Вы хотите снять: " + money);
            System.out.println("Недостаточное количество банкнот на счете для выдачи денежных средств");
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
}
