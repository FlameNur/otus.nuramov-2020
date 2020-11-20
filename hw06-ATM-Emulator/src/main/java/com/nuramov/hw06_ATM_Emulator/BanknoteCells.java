package com.nuramov.hw06_ATM_Emulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanknoteCells {
    //Map<Integer, Integer> banknoteCells = new HashMap<>();

    // Определяем количество банкнот для выдачи
    public static String countOfRubBanknote(Rub rub) {
        List<Integer> rub_denomination = new ArrayList<>();
        rub_denomination.add(Rub.rub_denomination_5000);
        rub_denomination.add(Rub.rub_denomination_1000);
        rub_denomination.add(Rub.rub_denomination_500);
        rub_denomination.add(Rub.rub_denomination_100);
        rub_denomination.add(Rub.rub_denomination_50);

        StringBuilder s = new StringBuilder();
        s.append("(Банкноты: ");

        int money = rub.getAmountOfMoney();

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
