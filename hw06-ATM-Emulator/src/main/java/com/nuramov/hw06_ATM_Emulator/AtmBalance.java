package com.nuramov.hw06_ATM_Emulator;

public class AtmBalance implements AtmAction{
    public static int balance = 0;

    @Override
    public void action() {
        System.out.println("Баланс Atm: " + balance + " рублей");
    }
}
