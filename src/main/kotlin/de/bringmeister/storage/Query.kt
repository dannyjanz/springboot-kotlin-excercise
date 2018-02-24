@file:Suppress("UNCHECKED_CAST")

package de.bringmeister.storage

interface Query<T> {

    fun select(pred: (T) -> Boolean): Query<T>
    fun <R> map(func: (T) -> R): Query<R>

    fun list(): List<T>
    fun single(): T
}


class SimpleSequenceQuery<T>(private val values: Sequence<T>) : Query<T> {

    override fun <R> map(func: (T) -> R): Query<R> = SimpleSequenceQuery(this.values.map(func))

    override fun select(pred: (T) -> Boolean): Query<T> = SimpleSequenceQuery(this.values.filter(pred))

    override fun list(): List<T> = values.toList()

    override fun single(): T = values.first()

}

