package com.nuramov.hw05_GC_Work_Monitoring;

import java.util.ArrayList;
import java.util.List;

public class MyTestClass {

    void run() throws InterruptedException {
        List<Object> myList = new ArrayList<>();
        int limit = 100_000;

        while (true) {
            for (int i = 0; i < limit; i++) {
                myList.add(new Object());
            }

            for (int i = 0; i < limit/2; i++) {
                myList.remove(myList.size() - 1);
            }
            Thread.sleep(10);
        }
    }
}
