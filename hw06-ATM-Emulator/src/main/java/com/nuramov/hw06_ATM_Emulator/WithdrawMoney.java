package com.nuramov.hw06_ATM_Emulator;

import java.util.ArrayList;
import java.util.List;

public class WithdrawMoney implements AtmAction {
    Rub rub;

    public WithdrawMoney(Rub rub) {
        this.rub = rub;
    }

    @Override
    public void action() {
        boolean chek = checkBalance();
        if(chek) return;

        // Уменьшаем баланс Atm
        AtmBalance.balance -= rub.amountOfMoney;
        System.out.println("Вы сняли: " + rub.amountOfMoney + " рублей " + countOfRubBanknote());
    }

    // Проверяем баланс Atm. Если денег недостаточно, возвращаем true для завершения операции
    private boolean checkBalance() {
        boolean b = false;
        if(AtmBalance.balance - rub.amountOfMoney < 0) {
            System.out.println("Недостаточно средств на счете");
            b = true;
        }
        return b;
    }

    // Определяем количество банкнот для выдачи
    private String countOfRubBanknote() {
        List<Integer> rub_denomination = new ArrayList<>();
        rub_denomination.add(Rub.rub_denomination_5000);
        rub_denomination.add(Rub.rub_denomination_1000);
        rub_denomination.add(Rub.rub_denomination_500);
        rub_denomination.add(Rub.rub_denomination_100);
        rub_denomination.add(Rub.rub_denomination_50);

        StringBuilder s = new StringBuilder();
        s.append("(Банкноты: ");

        int money = rub.amountOfMoney;

        for (int i = 0; i < rub_denomination.size(); i++) {

            // Количество каждой банкноты по убыванию
            int n = money / rub_denomination.get(i);
            if(n != 0) {
                money = money - (n * rub_denomination.get(i));
                s.append(rub_denomination.get(i)).append(" рублей x").append(n).append(", ");
            }
        }
        s.delete(s.length() - 2, s.length());
        s.append(")");
        return s.toString();
    }
}
