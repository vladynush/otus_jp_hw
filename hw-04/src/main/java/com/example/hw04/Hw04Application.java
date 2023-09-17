package com.example.hw04;

import com.example.hw04.tests.TestClassRunner;

public class Hw04Application {

    public static void main(String[] args) {
        TestClassRunner testRunner =
                new TestClassRunner("com.example.hw04.MainTest");
        testRunner.runTests();
    }

}
