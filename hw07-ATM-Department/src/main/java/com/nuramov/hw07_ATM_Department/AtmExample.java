package com.nuramov.hw07_ATM_Department;

import java.util.Map;
import java.util.TreeMap;

public class AtmExample implements Atm {
    private int balance;
    private boolean stateOFAtm = true;
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
        // Сохраняем состояние Atm перед выполнением операции/метода withdrawMoney
        versionController.setSave(save());

        // Выдаваемые банкноты
        Map<Integer, Integer> paymentCells = new TreeMap<>();

        // Проверка на достаточное количество средств на счете Atm
        if(!checkBalance(money)) {
            System.out.println("Недостаточно средств на счете");
            return;
        }

        // Возвращаем true, если все условия для выдачи денежных средств выполняются
        // Определяем количество выдаваемых банкнот
        boolean payment = withdrawStrategy.payment(money, paymentCells, banknoteCells);

        if(payment) {
            balance -= money;
        }
    }

    @Override
    public void depositMoney(Rub rub, int rubCount) {
        // Количество внесенных денежных средств
        int money = rub.getValue() * rubCount;
        System.out.println("Вы внесли: " + money + " рублей ");
        balance += money;

        // Увеличиваем количество банкноты
        int count = banknoteCells.get(rub.getValue()) + rubCount;
        banknoteCells.put(rub.getValue(), count);
    }

    // Проверяем баланс Atm
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
    public void setStateOFAtm(boolean stateOFAtm) {
        this.stateOFAtm = stateOFAtm;
    }

    @Override
    public void load(Save save) {
        banknoteCells = save.getSavedBanknoteCells();
        balance = save.getSavedBalance();
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

    private Save save() {
        int copyOfBalance = balance;

        // Копируем banknoteCells в новую Map copyOfBanknoteCells
        Map<Integer, Integer> copyOfBanknoteCells = new TreeMap<>(banknoteCells);
        return new Save(copyOfBanknoteCells, copyOfBalance);
    }
}
