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
    public void getAge(int aValue, String tName) {
        toString();
    }

    public void getName(String args) {
        long startTime = System.currentTimeMillis();
        long subTime = System.currentTimeMillis() - startTime;
        if (subTime > 30) {
            System.out.println("执行时间 " + subTime + " ms");
            System.out.println(args);
        }
    }

    @Override
    public String toString() {
        return "StudentJ{" +
                "age=" + age +
                "name=" + name +
                "}";
    }
}
