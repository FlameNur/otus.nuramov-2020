package com.nuramov.hw06_ATM_Emulator;

public class DepositMoney implements AtmAction{
    Rub rub;

    public DepositMoney(Rub rub) {
        this.rub = rub;
    }

    @Override
    public void action() {
        increaseOfBalance();
        System.out.println("Вы внесли: " + rub.amountOfMoney + " рублей");
    }

    // Увеличиваем баланс Atm
    private void increaseOfBalance() {
        AtmBalance.balance += rub.amountOfMoney;
    }
}
