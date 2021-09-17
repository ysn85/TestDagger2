package com.annotation

@MustBeDocumented
@Retention(value = AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class TestAnnotation() {
}
