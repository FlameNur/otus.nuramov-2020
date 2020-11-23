package com.nuramov.hw06_ATM_Emulator;

import java.util.ArrayList;
import java.util.List;

public class BanknoteCells {


    // Определяем количество банкнот для выдачи
    public static String countOfBanknote(int money) {

        StringBuilder s = new StringBuilder();
        s.append("(Банкноты: ");

        //int money = rub.getAmountOfMoney(); раньше так было и параметр был Rub rub

        for (int i = 0; i < Rub.rub_denomination.size(); i++) {

            // Количество каждой банкноты по убыванию
            int n = money / Rub.rub_denomination.get(i);
            if(n != 0) {
                money = money - (n * Rub.rub_denomination.get(i));
                s.append(Rub.rub_denomination.get(i)).append(" рублей x").append(n).append(", ");
            }
        }
        s.delete(s.length() - 2, s.length());
        s.append(")");
        return s.toString();
    }
}
