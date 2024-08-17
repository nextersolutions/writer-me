package io.writerme.core.presentation

interface EventHandler<T> {
    fun obtainEvent(event: T)
}
