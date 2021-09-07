package com.annotation

@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class PersonAnnotation(val name: String, val age: Int)
