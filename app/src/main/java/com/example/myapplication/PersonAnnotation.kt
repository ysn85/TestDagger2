package com.example.myapplication

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class PersonAnnotation(val name: String, val age: Int)