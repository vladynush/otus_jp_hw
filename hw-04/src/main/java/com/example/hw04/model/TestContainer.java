package com.example.hw04.model;

import com.example.hw04.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

public class TestContainer {
    final Object testClassInstance;
    final List<Method> beforeMethods;
    final Method test;
    final List<Method> afterMethods;

    public TestContainer(Object testClassInstance, List<Method> beforeMethods,
                         Method test, List<Method> afterMethods) {
        this.testClassInstance = testClassInstance;
        this.beforeMethods = beforeMethods;
        this.test = test;
        this.afterMethods = afterMethods;
    }

    public TestResult run() {
        try {
            ReflectionUtils.runMethods(testClassInstance, beforeMethods);
        } catch (Throwable e) {
            return TestResult.ABORTED;
        }
        TestResult testRes = ReflectionUtils.runTest(testClassInstance, test);
        try {
            ReflectionUtils.runMethods(testClassInstance, afterMethods);
        } catch (Throwable e) {
            return TestResult.ABORTED;
        }
        return testRes;
    }
}
