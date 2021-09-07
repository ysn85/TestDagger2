package com.annotation

import androidx.annotation.IdRes

@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class BtsOnClick(@IdRes val value: IntArray)
