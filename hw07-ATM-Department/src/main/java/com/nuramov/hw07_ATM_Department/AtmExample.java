package com.nuramov.hw07_ATM_Department;

import com.nuramov.hw07_ATM_Department.memento.Save;
import com.nuramov.hw07_ATM_Department.memento.VersionController;

import java.util.Map;
import java.util.TreeMap;

    /*
    class AtmExample представляет собой пример реализации работы Atm, его функционала.

    Поля класса AtmExample:
    - balance           - баланс Atm;
    - accessRightsToAtm - право доступа для дальнейшей работы Atm.
                          Если в Atm недостаточное количество банкнот для выдачи,
                          accessRightsToAtm = false пока не выполнится запрос
                          на восстановление состояния от AtmDepartment;
    - versionController - класс, который позволяет работать с сохраненными состояниями Atm через класс Save.
                          Выполняет роль class Caretaker паттерна Мементо (pattern Memento).
     */

public class AtmExample implements Atm {
    private int balance;
    private boolean accessRightsToAtm = true;
    private VersionController versionController;

    // Банкноты в Atm
    private Map<Integer, Integer> banknoteCells = new TreeMap<>();

    {
        banknoteCells.put(Rub.RUB_5000.getValue(), 0);
        banknoteCells.put(Rub.RUB_1000.getValue(), 0);
        banknoteCells.put(Rub.RUB_500.getValue(), 0);
        banknoteCells.put(Rub.RUB_100.getValue(), 0);
        banknoteCells.put(Rub.RUB_50.getValue(), 0);
    }

    public AtmExample(VersionController versionController) {
        this.versionController = versionController;

        // Сохраняем первичное состояние Atm
        this.versionController.setSave(save());
    }

    @Override
    public void withdrawMoney(int money, WithdrawStrategy withdrawStrategy) {
        // Проверяем права доступа для дальнейшей работы
        if(!accessRightsToAtm) return;

        // Сохраняем состояние Atm перед выполнением операции/метода withdrawMoney
        versionController.setSave(save());

        // Проверка на достаточное количество средств на счете Atm
        if(!checkBalance(money)) {
            System.out.println("Недостаточно средств на счете");
            return;
        }

        // Выдаваемые банкноты
        Map<Integer, Integer> paymentCells = new TreeMap<>();

        // Возвращаем true, если все условия для выдачи денежных средств выполняются
        // Определяем количество выдаваемых банкнот
        boolean payment = withdrawStrategy.payment(money, paymentCells, banknoteCells);

        if(payment) {
            balance -= money;
        } else accessRightsToAtm = false;
    }

    @Override
    public void depositMoney(Rub rub, int rubCount) {
        // Количество внесенных денежных средств
        int money = rub.getValue() * rubCount;
        System.out.println("Вы внесли: " + money + " рублей ");
        balance += money;

        // Увеличиваем количество банкнот
        int count = banknoteCells.get(rub.getValue()) + rubCount;
        banknoteCells.put(rub.getValue(), count);
    }

    @Override
    public void atmBalance() {
        System.out.println("Баланс Atm: " + balance + " рублей");
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public VersionController getVersionController() {
        return versionController;
    }

    @Override
    public void setAccessRightsToAtm(boolean accessRightsToAtm) {
        this.accessRightsToAtm = accessRightsToAtm;
    }

    @Override
    public void load(Save save) {
        banknoteCells = save.getSavedBanknoteCells();
        balance = save.getSavedBalance();
    }

    // Передаем текущие значения balance и banknoteCells в класс Save, чтобы сохранить состояние Atm
    private Save save() {
        int copyOfBalance = balance;
        Map<Integer, Integer> copyOfBanknoteCells = new TreeMap<>(banknoteCells);
        return new Save(copyOfBanknoteCells, copyOfBalance);
    }

    // Проверяем баланс Atm.
    // Если денег недостаточно, возвращаем false
    private boolean checkBalance(int money) {
        boolean b = true;
        if(balance - money < 0) {
            b = false;
        }
        return b;
    }
}
