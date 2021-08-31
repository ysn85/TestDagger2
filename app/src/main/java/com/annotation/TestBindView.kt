package com.annotation

import androidx.annotation.IdRes

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class TestBindView(@IdRes val value: Int)
