package com.dagger;

import dagger.Component;

@Component(modules = {MenuModule.class, JPersonModule.class, PersonModule.class})
public interface MainComponent {
    void inject(DaggerActivity activity);
}
