package com.dagger;

import dagger.Module;
import dagger.Provides;

@Module
public class JPersonModule {

    @Provides
    public JCustomer providesJCustomer() {
        return new JCustomer();
    }
}
