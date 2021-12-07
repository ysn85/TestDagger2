package com.annotation;

import androidx.annotation.RestrictTo;

import com.sk.dagger.bts_annotation.FuncTimeCost;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class StudentJ {
    @TestAnnotationJ
    String name;
    int age;

    @FuncTimeCost
    public void getAge() {
    }

    @Override
    public String toString() {
        return "StudentJ{" +
                "age=" + age +
                "name=" + name +
                "}";
    }
}
