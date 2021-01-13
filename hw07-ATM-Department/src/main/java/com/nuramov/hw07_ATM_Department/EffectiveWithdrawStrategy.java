package com.nuramov.hw07_ATM_Department;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/** class EffectiveWithdrawStrategy позволяет выдать денежные средства наименьшим количеством банкнот,
    от банкнот с максимальным весом к минимальным */

public class EffectiveWithdrawStrategy implements WithdrawStrategy {

    @Override
    public boolean payment(int money, Map<Integer, Integer> paymentCells, Map<Integer, Integer> banknoteCells) {
        // Реверсивная сортировка Map banknoteCells (от 5000 до 50)
        Map<Integer, Integer> reversedBanknoteCells = new TreeMap<>(Comparator.reverseOrder());
        reversedBanknoteCells.putAll(banknoteCells);

        // Определяем количество выдываемых банкнот paymentCells
        banknoteCountToPay(money, paymentCells, banknoteCells, reversedBanknoteCells);

        // sum - сумму всех выдываемых банкнот
        int sum = 0;
        for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
            sum += m.getKey() * m.getValue();
        }

        // Определяем условия выдачи денежных средств
        return withdrawalCondition(sum, money, paymentCells, banknoteCells);
    }

    /** Определяем количество выдываемых банкнот paymentCells
     * @param localMoney                - запрошенные для выдачи денежные средства;
     * @param paymentCells              - количество выдываемых банкнот;
     * @param banknoteCells             - количество банкнот в Atm;
     * @param reversedBanknoteCells     - Реверсивная сортировка banknoteCells (от 5000 до 50).
     */
    private void banknoteCountToPay(int localMoney,
                                    Map<Integer, Integer> paymentCells,
                                    Map<Integer, Integer> banknoteCells,
                                    Map<Integer, Integer> reversedBanknoteCells) {

        /* В цикле определяем:
        1 - наличие банкноты (условие);
        2 - количество конкретной банкноты для погашения части/всей суммы (переменная - n);
        3 - проверяем корректность значения переменной - n исходя из имеющегося значения m.getValue() (условие)
        4 - уменьшаем общую сумму исходя из значения переменной - n и номинала банкноты
        5, 6 - добавляем соответствующую банкноту в Map paymentCells и удаляем ее количество из Map banknoteCells
        7 - если для погашения суммы требуется большее количество банкнот, чем имеется,
        погашаем чем имеем. Повторяем пп. 5 и 6  */
        for (Map.Entry<Integer, Integer> m : reversedBanknoteCells.entrySet()) {
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
    }

    /** Определяем условия выдачи денежных средств
     * @param sum               - сумму всех выдываемых банкнот;
     * @param money             - запрошенные для выдачи денежные средства;
     * @param paymentCells      - количество выдываемых банкнот;
     * @param banknoteCells     - количество банкнот в Atm
     * @return true - если есть возможность выдать денежные средства, false - если нет возможности
     */
    private boolean withdrawalCondition (int sum,
                                         int money,
                                         Map<Integer, Integer> paymentCells,
                                         Map<Integer, Integer> banknoteCells) {

        // Если money <= баланс Atm (balance) и количество банкнот в banknoteCells (sum == money) хватает для выдачи,
        // то выдается запрошенная сумма денежных средств
        if(sum == money) {
            System.out.println("Вы сняли: " + money + " рублей ");
            System.out.println("Количество выданных банкнот: ");
            for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
                System.out.println(m.getKey() + " - x" + m.getValue());
            }
            return true;
        } else {
            for (Map.Entry<Integer, Integer> m : paymentCells.entrySet()) {
                // Если банкнот в banknoteCells недостаточно для выдачи,
                // банкноты обратно возвращаются в banknoteCells
                banknoteCells.put(m.getKey(), m.getValue());
            }
            System.out.println("Вы хотите снять: " + money);
            System.out.println("Недостаточное количество банкнот на счете для выдачи денежных средств");
            return false;
        }
    }
}
