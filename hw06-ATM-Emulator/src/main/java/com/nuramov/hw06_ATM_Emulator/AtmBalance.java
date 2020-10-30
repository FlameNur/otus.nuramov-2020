package com.nuramov.hw06_ATM_Emulator;

public class AtmBalance implements AtmAction {
    public int balance;

    @Override
    public void action() {

        System.out.println(balance);
    }
}
