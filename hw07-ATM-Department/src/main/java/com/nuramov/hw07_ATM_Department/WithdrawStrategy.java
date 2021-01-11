package com.nuramov.hw07_ATM_Department;

import java.util.Map;

    /*
    interface WithdrawStrategy определяем количество банкнот для выдачи и
    позволяет использовать разные методы выдачи денежных средств
    */

public interface WithdrawStrategy {

    /* Возвращаем true, если все условия для выдачи денежных средств выполняются.
    money         - количество выдаваемых денежных средств;
    paymentCells  - количество выдываемых банкнот;
    banknoteCells - количество оставшихся банкнот в Atm  */
    boolean payment(int money, Map<Integer, Integer> paymentCells, Map<Integer, Integer> banknoteCells);
}
