package com.sk.dagger.bts_plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class HelloJPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        System.out.println("hello java plugin " + target.getRootDir().getAbsolutePath());
    }
}
