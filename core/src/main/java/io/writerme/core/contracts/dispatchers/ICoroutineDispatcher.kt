package io.writerme.core.contracts.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface ICoroutineDispatcher {
    val ui: CoroutineDispatcher
    val io: CoroutineDispatcher
}

class CoroutineDispatcher @Inject constructor() : ICoroutineDispatcher {
    override val ui: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
}
