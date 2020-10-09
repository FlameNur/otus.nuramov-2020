package com.nuramov.hw04AutoLogging;

import com.nuramov.hw04AutoLogging.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyProxyClass {

    static Object classCreater(Object myClass) {
        InvocationHandler handler = new MyInvocationHandler(myClass);
        ClassLoader myLoader = MyProxyClass.class.getClassLoader();
        Class<?>[] interfaces = myClass.getClass().getInterfaces();
        Object proxyObj = Proxy.newProxyInstance(myLoader, interfaces, handler);
        return proxyObj;
    }

    private static class MyInvocationHandler implements InvocationHandler {
        private Object objInterface;

        public MyInvocationHandler(Object objInterface) {
            this.objInterface = objInterface;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if(method.isAnnotationPresent(Log.class)) {
                System.out.print("Executed method: " + method.getName() + ", params: ");

                // Параметры метода
                Object[] params = Arrays.stream(args).toArray();
                String[] paramsToString = Arrays.stream(params).map(Object::toString).toArray(String[]::new);
                List<String> paramsToList = new ArrayList<String>(Arrays.asList(paramsToString));

                // Добавляется разделитель между параметрами метода - ", "
                String allParamsToString = String.join(", ", paramsToList);
                System.out.println(allParamsToString);
            }
            return method.invoke(objInterface, args);
        }
    }
}
