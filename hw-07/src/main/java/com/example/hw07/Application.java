package com.example.hw07;

public class Application {

    public static void main(String[] args) {
        LoggingInterface testLog = CustomProxy.createInstance();
        testLog.calculation();
        testLog.calculation(1);
        testLog.calculation(1, 2);
        testLog.calculation(1, "2");
        testLog.calculation(1, 2, 3);
        testLog.calculation(1, 2, 3, 4);
    }

}
