package com.nuramov.hw06_ATM_Emulator;

public class DepositMoney implements AtmAction{
    Rub rub;
    AtmBalance myBalance = new AtmBalance();

    public DepositMoney(Rub rub) {
        this.rub = rub;
    }

    @Override
    public void action() {
        increaseOfBalance();
        System.out.println("Внести на счет: " + rub.amountOfMoney + " рублей");
    }

    private void increaseOfBalance() {
        myBalance.balance += rub.amountOfMoney;

    }
}
