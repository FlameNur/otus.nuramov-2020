package com.nuramov.hw06_ATM_Emulator;

import java.util.Map;

/*
Интерфейс позволяет использовать разные методы выдачи денежных средств
 */

public interface WithdrawStrategy {

    // Определяем количество банкнот для выдачи
    // Возвращаем true, если все условия для выдачи денежных средств выполняются
    // Map paymentCells определяет требуемое количество банкнот
    // Map banknoteCells опредеяет оставшиееся количество банкнот в Atm
    boolean payment(int money, Map<Integer, Integer> paymentCells, Map<Integer, Integer> banknoteCells);
}
