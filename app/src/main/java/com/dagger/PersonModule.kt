package com.dagger

import dagger.Module
import dagger.Provides

@Module
class PersonModule {
    @Provides
    fun providesCustomer(): Customer {
        return Customer()
    }
}