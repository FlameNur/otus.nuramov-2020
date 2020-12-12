package com.nuramov.hw06_ATM_Emulator;

import java.util.Map;

/*
Интерфейс позволяет использовать разные методы выдачи денежных средств
 */

public interface WithdrawStrategy {

    // Определяем количество банкнот для выдачи
    // Возвращаем Map с требуемым количеством банкнот
    Map<Integer, Integer> payment(int money, Map<Integer, Integer> paymentCells, Map<Integer, Integer> banknoteCells);
}
