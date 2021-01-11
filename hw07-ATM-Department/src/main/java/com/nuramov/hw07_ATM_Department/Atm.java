package com.nuramov.hw07_ATM_Department;

    /* Общий interface для всех Atm */

public interface Atm {

    // Выводим денежные средства из Atm.
    // money - количество денежных средств
    // withdrawStrategy - стратегия выдачи банкнот
    void withdrawMoney(int money, WithdrawStrategy withdrawStrategy);

    // Вводим денежные средства в Atm
    // rub - номинал банкнот
    // moneyCount - количество банкнот
    void depositMoney(Rub rub, int moneyCount);

    // Проверяем баланс Atm
    void atmBalance();

    // Получаем значение баланса Atm
    int getBalance();

    // Устанавливаем значение accessRightsToAtm = false для закрытия доступа к выдаче денежных средств
    // в случае отсутсвия требуемого количества банкнот для выдачи.
    // accessRightsToAtm = true при выполнении запроса от AtmDepartment для восстановления предыдущего состояния
    void setAccessRightsToAtm(boolean accessRightsToAtm);

    // Получаем текущий VersionController, чтобы получить сохраненное состояние Atm
    VersionController getVersionController();

    // Загружаем сохраненное состояние Atm
    void load(Save save);
}
