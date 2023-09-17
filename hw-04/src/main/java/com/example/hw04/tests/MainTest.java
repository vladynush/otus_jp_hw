package com.example.hw04.tests;

import com.example.hw04.annotation.After;
import com.example.hw04.annotation.Before;
import com.example.hw04.annotation.Test;

public class MainTest {


    @Before
    public void init() {
        System.out.println("Hello from stage before test");
    }

    @After
    public void after() {
        System.out.println("Goodbye from stage after test");
    }


    @Test
    public void fstFailedTest() {
        System.out.println("this test should fails");
        throw new AssertionError("test with exception was launched");
    }

    @Test
    public void fstAbortedTest() {
        System.out.println("this test should be aborted 1");
        throw new RuntimeException("test with exception was launched");
    }

    @Test
    public void fstSuccessTest() {
        System.out.println("first test running");
    }

    @Test
    public void sndSuccessTest() {
        System.out.println("snd test running");
    }

    @Test
    public void sndAbortedTest() {
        System.out.println("this test should be aborted 2");
        throw new RuntimeException("test with exception was launched");
    }
}
