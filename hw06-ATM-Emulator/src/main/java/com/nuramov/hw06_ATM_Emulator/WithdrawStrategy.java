package com.nuramov.hw06_ATM_Emulator;

import java.util.Map;

public interface WithdrawStrategy {

    // Определяем количество банкнот для выдачи.
    // Возвращаем Map paymentCells с требуемым количеством банкнот
    Map<Integer, Integer> payment(int money, Map<Integer, Integer> paymentCells, Map<Integer, Integer> banknoteCells);
}
