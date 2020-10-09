package com.nuramov.hw04AutoLogging;

import com.nuramov.hw04AutoLogging.annotations.Log;

public interface MyInterface {

    public void method1 (int param1, int param2);

    @Log
    public void method2 (int param1, int param2, String param3);

}
