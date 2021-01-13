package com.nuramov.hw07_ATM_Department;

    /** Общий interface для всех Atm */

import com.nuramov.hw07_ATM_Department.memento.Save;
import com.nuramov.hw07_ATM_Department.memento.VersionController;

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

    /** Проверяем баланс Atm */
    void atmBalance();

    /** Получаем значение баланса Atm */
    int getBalance();

    /** Устанавливаем право доступа к методу withdrawMoney.
     * При отсутсвии требуемого количества банкнот для выдачи - accessRightsToAtm = false.
     * При выполнении запроса от AtmDepartment для восстановления предыдущего состояния Atm - accessRightsToAtm = true.
     * @param accessRightsToAtm - право доступа к методу withdrawMoney
     */
    void setAccessRightsToAtm(boolean accessRightsToAtm);

    /** Получаем текущий VersionController, чтобы получить сохраненное состояние Atm */
    VersionController getVersionController();

    /** Загружаем сохраненное состояние Atm
     * @param save - сохраненное состояние Atm
     */
    void load(Save save);
}
