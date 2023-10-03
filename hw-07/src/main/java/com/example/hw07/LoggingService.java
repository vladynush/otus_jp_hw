package com.example.hw07;

public class LoggingService implements LoggingInterface {
    @Override
    public void calculation() {
        System.out.println("This is no args method\n");
    }

    @Log
    @Override
    public void calculation(Object arg) {
        System.out.println("This is single arg method\n");
    }

    @Log
    @Override
    public void calculation(Object arg1, Object arg2) {
        System.out.println("This is two args method\n");
    }

    @Override
    public void calculation(Object arg1, Object arg2, Object arg3) {
        System.out.println("This is threes method\n");
    }

    @Override
    @Log
    public void calculation(Object... args) {
        System.out.println("This is multi args method\n");
    }

}