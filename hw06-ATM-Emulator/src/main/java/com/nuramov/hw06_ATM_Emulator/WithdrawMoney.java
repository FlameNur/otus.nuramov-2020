package com.nuramov.hw06_ATM_Emulator;

public class WithdrawMoney implements AtmAction {
    Rub rub;
    AtmBalance myBalance = new AtmBalance();

    public WithdrawMoney(Rub rub) {
        this.rub = rub;
    }

    @Override
    public void action() {
        checkBalance();

        myBalance.balance -= rub.amountOfMoney;
        System.out.println("Вывод со счета: " + rub.amountOfMoney + " рублей");
    }

    // Проверяем баланс. Если денег недостаточно, выводим ошибку
    private void checkBalance() {
        if(myBalance.balance - rub.amountOfMoney < 0) {
            System.out.println("Недостаточно средств на счете");
            throw new RuntimeException();
        }
    }
}
