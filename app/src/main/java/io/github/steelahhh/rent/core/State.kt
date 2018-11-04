package io.github.steelahhh.rent.core

/*
 * Created by Alexander Efimenko on 4/11/18.
 */

sealed class State<out T, out E> {
    data class Success<out T, out E>(val data: T) : State<T, E>()
    data class Error<out T, out E>(val error: E) : State<T, E>()

    fun isSuccess(): Boolean = this is Success
    fun isError(): Boolean = this is Error

    fun dataValue(): T? = (this as? Success)?.data

    fun errorValue(): E? = (this as? Error)?.error
}
