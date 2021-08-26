package com.dagger;

import android.util.Log;

import javax.inject.Inject;

public class Chef {

    private static final String TAG = "Chef";

    private String name;

    public Chef() {
    }

    @Inject
    public Chef(String name) {
        this.name = name;
    }

    public void cook() {
        Log.i(TAG, "cooker name is " + name);
    }
}
