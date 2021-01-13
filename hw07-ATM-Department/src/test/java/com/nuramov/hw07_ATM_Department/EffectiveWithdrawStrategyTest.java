package com.nuramov.hw07_ATM_Department;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

/** Тест проводит проверку метода payment класса EffectiveWithdrawStrategy */

class EffectiveWithdrawStrategyTest {
    private static WithdrawStrategy withdrawStrategy;
    private static Map<Integer, Integer> banknoteCells;

    @BeforeAll
    static void initBanknoteCells() {
        banknoteCells = new TreeMap<>();
    }

    @BeforeEach
    void BanknoteCells() {
        banknoteCells.put(Rub.RUB_5000.getValue(), 1);
        banknoteCells.put(Rub.RUB_1000.getValue(), 2);
        banknoteCells.put(Rub.RUB_500.getValue(), 3);
        banknoteCells.put(Rub.RUB_100.getValue(), 1);
        banknoteCells.put(Rub.RUB_50.getValue(), 3);
    }

    @BeforeEach
    void initStrategy() {
        withdrawStrategy = new EffectiveWithdrawStrategy();
    }

    @BeforeEach
    void TestStart() {
        System.out.println("Наличие банкнот на начало операции:");
        for (Map.Entry<Integer, Integer> m : banknoteCells.entrySet()) {
            System.out.println(m.getKey() + " - x" + m.getValue());
        }
        System.out.println("\n" + "Начало Теста:");
    }

    @Test
    void PaymentTrueTest() {
        Map<Integer, Integer> paymentCells = new TreeMap<>();
        boolean test = withdrawStrategy.payment(8750, paymentCells, banknoteCells);
        assertTrue(test);
    }

    @Test
    void PaymentFalseTest() {
        Map<Integer, Integer> paymentCells = new TreeMap<>();
        boolean test = withdrawStrategy.payment(350, paymentCells, banknoteCells);
        assertFalse(test);
    }

    @AfterEach
    void TestEnd() {
        System.out.println("\n" + "Наличие банкнот на конец операции:");
        for (Map.Entry<Integer, Integer> m : banknoteCells.entrySet()) {
            System.out.println(m.getKey() + " - x" + m.getValue());
        }
        System.out.println("Тест успешно завершен");
    }
}