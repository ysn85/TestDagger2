package com.dagger;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

public class DaggerActivity extends AppCompatActivity {

    @Inject
    Chef chef;
    @Inject
    Waiter waiter;
    @Inject
    JCustomer jCustomer;
    @Inject
    Customer customer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainComponent.create().inject(this);
//        DaggerMainComponent.builder().build().inject(this);
        chef.cook();
        waiter.service();
        jCustomer.eat();
        customer.eat();
    }
}
