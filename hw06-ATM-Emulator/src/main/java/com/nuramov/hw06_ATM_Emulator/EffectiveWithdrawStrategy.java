package com.nuramov.hw06_ATM_Emulator;

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
    public Map<Integer, Integer> payment(int money, Map<Integer, Integer> paymentCells, Map<Integer, Integer> banknoteCells) {
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
