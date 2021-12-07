package com.annotation;

import androidx.annotation.RestrictTo;

import com.sk.dagger.bts_annotation.FuncTimeCost;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class StudentJ {
    @TestAnnotationJ
    String name;
    int age;
    private int classNum;

    @FuncTimeCost
    public void getAge() {
        toString();
    }

    public void getName() {
        long startTime = System.currentTimeMillis();
        System.out.println("执行时间 " + (System.currentTimeMillis() - startTime) + " ms");
    }

    @Override
    public String toString() {
        return "StudentJ{" +
                "age=" + age +
                "name=" + name +
                "}";
    }
}
