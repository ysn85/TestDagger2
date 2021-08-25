package com.dagger;

import dagger.Module;
import dagger.Provides;

@Module
public class MenuModule {

    @Provides
    Chef providesChef() {
        return new Chef();
    }

    @Provides
    Waiter providesWaiter() {
        return new Waiter();
    }
}
