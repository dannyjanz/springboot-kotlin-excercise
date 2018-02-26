@file:Suppress("UNCHECKED_CAST")

package de.bringmeister.storage

import de.bringmeister.util.Attempt
import de.bringmeister.util.Empty
import de.bringmeister.util.Result
import de.bringmeister.util.Success

interface Query<T> {

    fun select(pred: (T) -> Boolean): Query<T>
    fun <R> join(func: (T) -> Result<R>): Query<R>
    fun <R> map(func: (T) -> R): Query<R>

    fun list(): Result<List<T>>
    fun single(): Result<T>

    companion object {
        fun <T> emptyQueryResult() = Empty<T>("Query returned no result")
    }
}


class SimpleSequenceQuery<T>(private val values: Sequence<T>) : Query<T> {

    override fun <R> map(func: (T) -> R): Query<R> = SimpleSequenceQuery(this.values.map(func))

    override fun <R> join(func: (T) -> Result<R>): Query<R> = JoinedSequenceQuery(this.values.map(func))

    override fun select(pred: (T) -> Boolean): Query<T> = SimpleSequenceQuery(this.values.filter(pred))

    override fun list(): Result<List<T>> = Attempt { values.toList() }

    override fun single(): Result<T> = Attempt { values.first() }.recoverWith { Query.emptyQueryResult<T>() }

}

class JoinedSequenceQuery<T>(val result: Sequence<Result<T>>) : Query<T> {

    override fun select(pred: (T) -> Boolean): Query<T> = JoinedSequenceQuery(this.result.filter {
        when (it.filter(pred)) {
            is Success -> true
            else -> false
        }
    })

    override fun <R> join(func: (T) -> Result<R>): Query<R> = JoinedSequenceQuery(this.result.map { it.flatMap(func) })

    override fun <R> map(func: (T) -> R): Query<R> = JoinedSequenceQuery(this.result.map { it.map(func) })

    override fun list(): Result<List<T>> {
        var newResult = listOf<T>()
        for (element in result.toList()) {
            when (element) {
                is Success -> newResult += element.value
                else -> return element as Result<List<T>>
            }
        }
        return Success(newResult)
    }

    override fun single(): Result<T> = Attempt.identity.flatMap { result.first() }.recoverWith { Query.emptyQueryResult<T>() }

}

