package dev.smartification.reducers

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class HiltReducer(val value: KClass<out Any>)
