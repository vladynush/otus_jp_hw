package com.example.hw04.tests;

import com.example.hw04.annotation.After;
import com.example.hw04.annotation.Before;
import com.example.hw04.annotation.Test;
import com.example.hw04.model.TestContainer;
import com.example.hw04.model.TestResult;
import com.example.hw04.utils.ReflectionUtils;
import com.example.hw04.utils.StatsPrinter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestClassRunner {
    private final Class<?> classWithTests;

    public TestClassRunner(String classWithTests) {
        try {
            this.classWithTests = Class.forName(classWithTests);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                    String.format("Class %s is not found in classpath%n", classWithTests)
            );
        }
    }

    public void runTests() {
        List<Method> beforeMethods =
                ReflectionUtils.findMethodsWithAnnotation(classWithTests, Before.class);
        List<Method> afterMethods =
                ReflectionUtils.findMethodsWithAnnotation(classWithTests, After.class);
        List<Method> tests =
                ReflectionUtils.findMethodsWithAnnotation(classWithTests, Test.class);
        Object testClassInstance = ReflectionUtils.createNewInstance(classWithTests);
        List<String> aborted = new ArrayList<>();
        List<String> failed = new ArrayList<>();
        List<TestResult> succeed = new ArrayList<>();
        for (Method test : tests) {
            TestResult res = new TestContainer(testClassInstance, beforeMethods, test, afterMethods).run();
            switch (res) {
                case SUCCEED -> succeed.add(res);
                case FAILED -> failed.add(test.getName());
                case ABORTED -> aborted.add(test.getName());
            }
        }
        StatsPrinter.print(aborted, failed, succeed);
    }

}
