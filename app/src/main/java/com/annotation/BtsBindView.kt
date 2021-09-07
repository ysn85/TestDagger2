package com.annotation

import androidx.annotation.IdRes

@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class BtsBindView(@IdRes val value: Int)
