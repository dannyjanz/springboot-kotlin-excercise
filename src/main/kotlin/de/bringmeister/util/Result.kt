@file:Suppress("UNCHECKED_CAST")

package de.bringmeister.util

interface Result<out T> {

    fun <R> map(func: (T) -> R): Result<R>
    fun <R> flatMap(func: (T) -> Result<R>): Result<R>
    fun filter(pred: (T) -> Boolean): Result<T>
    fun <R> recoverWith(func: (Throwable) -> Result<R>): Result<R>

}

object Attempt {

    operator inline fun <T> invoke(expression: () -> T?) : Result<T> = try {
        expression()?.let { Success(it) } ?: Empty<T>("expression returned null") as Result<T>
    } catch (e: Throwable) {
        Failure(e)
    }

    val identity = Success(Unit)

}

data class Success<out T>(val value: T) : Result<T> {
    override fun <R> map(func: (T) -> R): Result<R> = Attempt { (func(value)) }

    override fun <R> flatMap(func: (T) -> Result<R>): Result<R> = try {
        func(value)
    } catch (e: Throwable) {
        Failure(e)
    }

    override fun filter(pred: (T) -> Boolean): Result<T> =
            if (pred(value)) Success(value) else Empty("Predicate did not hold for value $value")

    override fun <R> recoverWith(func: (Throwable) -> Result<R>): Result<R> = this as Result<R>
}

data class Empty<out T>(val message: String = "") : Result<T> {

    override fun <R> map(func: (T) -> R): Result<R> = this as Result<R>
    override fun <R> flatMap(func: (T) -> Result<R>): Result<R> = this as Result<R>
    override fun filter(pred: (T) -> Boolean): Result<T> = this
    override fun <R> recoverWith(func: (Throwable) -> Result<R>): Result<R> = this as Result<R>
}

data class Failure<out T>(val error: Throwable) : Result<T> {

    override fun <R> map(func: (T) -> R): Result<R> = this as Result<R>
    override fun <R> flatMap(func: (T) -> Result<R>): Result<R> = this as Result<R>
    override fun filter(pred: (T) -> Boolean): Result<T> = this
    override fun <R> recoverWith(func: (Throwable) -> Result<R>): Result<R> = func(error)
}

