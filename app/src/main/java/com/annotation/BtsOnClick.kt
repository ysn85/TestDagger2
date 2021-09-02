package com.annotation

import androidx.annotation.IdRes

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BtsOnClick(@IdRes val value: IntArray)
