package com.nuramov.hw08_JSON_Object_Writer.testObjects;


public class Ford extends Car {
    private final String model = "Explorer";
    private final int price = 1000;

    @Override
    public String toString() {
        return "Ford{" +
                "model='" + model + '\'' +
                ", price=" + price +
                '}';
    }
}
