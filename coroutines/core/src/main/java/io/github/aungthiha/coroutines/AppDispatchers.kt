package io.github.aungthiha.coroutines

import io.github.aungthiha.coroutines.TestDispatcherHolder.testDefault
import io.github.aungthiha.coroutines.TestDispatcherHolder.testIo
import io.github.aungthiha.coroutines.TestDispatcherHolder.testMain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.annotations.VisibleForTesting

object AppDispatchers {
    val main: CoroutineDispatcher get() = testMain ?: Dispatchers.Main
    val io: CoroutineDispatcher get() = testIo ?: Dispatchers.IO
    val default: CoroutineDispatcher get() = testDefault ?: Dispatchers.Default
}

@VisibleForTesting
object TestDispatcherHolder {
    var testMain: CoroutineDispatcher? = null
    var testIo: CoroutineDispatcher? = null
    var testDefault: CoroutineDispatcher? = null
}
