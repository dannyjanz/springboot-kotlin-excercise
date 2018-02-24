package de.bringmeister.storage

interface Queryable<T> {

    val query: Query<T>
}