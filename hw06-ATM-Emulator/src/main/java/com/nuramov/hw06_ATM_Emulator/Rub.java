package com.nuramov.hw06_ATM_Emulator;

public class Rub {

    // Номиналы банкнот
    public static final int rub_denomination_50 = 50;
    public static final int rub_denomination_100 = 100;
    public static final int rub_denomination_500 = 500;
    public static final int rub_denomination_1000 = 1000;
    public static final int rub_denomination_5000 = 5000;

    // Количество денег, выбранное для проведения операции в банкомате
    private int amountOfMoney;

    public Rub(int money) {
        if (money == 0) {
            System.out.println("Ошибка! Количество денег равно 0");
        }

        if(money % rub_denomination_50 == 0) {
            this.amountOfMoney = money;
        } else {
            System.out.println("Ошибка! Отсутсвует требуемый номинал денег");
        }
    }

    public int getAmountOfMoney() {
        return amountOfMoney;
    }
}
