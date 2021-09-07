package com.annotation

@MustBeDocumented
@Retention(value = AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class TestAnnotation() {
}
