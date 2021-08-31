package com.annotation

import androidx.annotation.IdRes

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestOnClick(@IdRes val value: Int)
