package com.nuramov.hw06_ATM_Emulator;

public class AtmExample implements Atm {
    private int balance;

    @Override
    public void withdrawMoney(Rub rub) {
        // Проверка на достаточное количество средств на счете Atm
        if(checkBalance(rub)) return;

        balance -= rub.getAmountOfMoney();
        System.out.print("Вы сняли: " + rub.getAmountOfMoney() + " рублей ");
        System.out.println(BanknoteCells.countOfRubBanknote(rub));
    }

    @Override
    public void depositMoney(Rub rub) {
        System.out.print("Вы внесли: " + rub.getAmountOfMoney() + " рублей ");
        balance += rub.getAmountOfMoney();
        System.out.println(BanknoteCells.countOfRubBanknote(rub));
    }

    @Override
    public void atmBalance() {
        System.out.println("Баланс Atm: " + balance + " рублей");
    }

    // Проверяем баланс Atm. Если денег недостаточно, возвращаем true для завершения операции
    private boolean checkBalance(Rub rub) {
        boolean b = false;
        if(balance - rub.getAmountOfMoney() < 0) {
            System.out.println("Недостаточно средств на счете");
            b = true;
        }
        return b;
    }
}
