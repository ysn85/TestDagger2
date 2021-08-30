package com.annotation;

public class StudentJ {
    String name;
    int age;

    @Deprecated
    public void getAge() {
    }

    @Override
    public String toString() {
        return "StudentJ{" +
                "age=" + age +
                '}';
    }
}
