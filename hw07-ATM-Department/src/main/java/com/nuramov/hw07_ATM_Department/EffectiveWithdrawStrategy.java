package com.nuramov.hw07_ATM_Department;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/*
Класс позволяет выдать денежные средства наименьшим количеством банкнот
от максимального значения к минимальному
 */

public class EffectiveWithdrawStrategy implements WithdrawStrategy {

    // Определяем количество банкнот для выдачи.
    // Возвращаем Map paymentCells с требуемым количеством банкнот
    @Override
    public boolean payment(int money, Map<Integer, Integer> paymentCells, Map<Integer, Integer> banknoteCells) {
        boolean payment = false;
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
                    localMoney -= (m.getValue() * m.getKey());
                    paymentCells.put(m.getKey(), m.getValue());
                    banknoteCells.put(m.getKey(), 0);
                }
            }
        }

        int sum = 0;

        // Определяем сумму (sum) всех банкнот для последующей проверки случая,
        // когда запрошенная сумма для выдачи меньше баланса Atm (balance),
        // но для ее выдачи не хватает требуемых банкнот
        for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
            sum += m.getKey() * m.getValue();
        }

        // Если запрошенное количество денежных средств удовлетворяет требованиям,
        // т.е. если money <= баланс Atm (balance) и количество банкнот в banknoteCells (sum == money) хватает для выдачи,
        // то выдается запрошенная сумма денежных средств
        if(sum == money) {
            System.out.println("Вы сняли: " + money + " рублей ");
            System.out.println("Количество выданных банкнот: ");
            for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
                System.out.println(m.getKey() + " - x" + m.getValue());
            }
            payment = true;
        } else {
            for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
                // Банкноты сначала снимаются из banknoteCells, а если их недостаточно для выдачи,
                // обратно возвращаются в banknoteCells
                banknoteCells.put(m.getKey(), m.getValue());
            }
            System.out.println("Вы хотите снять: " + money);
            System.out.println("Недостаточное количество банкнот на счете для выдачи денежных средств");
        }
        return payment;
    }
}
