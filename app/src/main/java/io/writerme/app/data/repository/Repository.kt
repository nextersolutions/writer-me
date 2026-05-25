package io.writerme.app.data.repository

abstract class Repository {
    private var currentId = System.currentTimeMillis()

    @Synchronized
    protected fun nextId(): Long = currentId++
}
