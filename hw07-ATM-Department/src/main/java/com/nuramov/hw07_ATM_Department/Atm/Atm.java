package com.nuramov.hw07_ATM_Department.Atm;

import com.nuramov.hw07_ATM_Department.MonetaryCurrency.Rub;
import com.nuramov.hw07_ATM_Department.WithdrawStrategies.WithdrawStrategy;

/** Общий interface для всех Atm */

public interface Atm {

    /** Выводим денежные средства из Atm.
     * @param money - количество денежных средств
     * @param withdrawStrategy - стратегия выдачи банкнот
     */
    void withdrawMoney(int money, WithdrawStrategy withdrawStrategy);

    /** Вводим денежные средства в Atm
     * @param rub - номинал банкнот
     * @param moneyCount - количество банкнот
     */
    void depositMoney(Rub rub, int moneyCount);

    /** Получаем значение баланса Atm */
    int getBalance();

    /** Устанавливаем право доступа к методу withdrawMoney.
     * При отсутсвии требуемого количества банкнот для выдачи - accessRightsToAtm = false.
     * При выполнении запроса от AtmDepartment для восстановления предыдущего состояния Atm - accessRightsToAtm = true.
     * @param accessRightsToAtm - право доступа к методу withdrawMoney
     */
    void setAccessRightsToAtm(boolean accessRightsToAtm);

    /** Загружаем предыдущее сохраненное состояние Atm */
    void loadPreviousState();

    /** Загружаем первичное сохраненное состояние Atm */
    void loadInitialState();
}
