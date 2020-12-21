package com.nuramov.hw06_ATM_Emulator;

import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

    /*
    Тест проводит проверку метода payment класса EffectiveWithdrawStrategy.
    Integer money (параметр метода payment) - определяет выводимые из Atm денежные средства.
    Map paymentCells - опредеяет банкноты для выдачи.
    Map banknoteCells - определяет имеющиеся в Atm банкноты.
    */

public class TestEffectiveWithdrawStrategy {
    private static Atm atmExample;
    private static WithdrawStrategy withdrawStrategy;
    private static Map<Integer, Integer> banknoteCells;

    @BeforeAll
    static void initAtm() {
        atmExample = new AtmExample();
    }

    @BeforeAll
    static void initStrategy() {
        withdrawStrategy = new EffectiveWithdrawStrategy();
    }

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
