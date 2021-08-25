package com.example.myapplication;

public class TestInfer {
    private String name = null;
    private int len = name.length();
    private String title = "123";
    private byte bytes = title.getBytes()[4];
}
