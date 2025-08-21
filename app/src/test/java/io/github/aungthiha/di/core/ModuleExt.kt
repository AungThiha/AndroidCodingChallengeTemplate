package io.github.aungthiha.di.core

import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.binds

/**
 * User interface as first generic parameter and implementation as second generic parameter.
* */
inline fun <reified I : Any, reified C : I> Module.testDouble(qualifier: Qualifier? = null, noinline factory: () -> C) {
    single(qualifier) { ThreadScoped(factory).instance } binds arrayOf(I::class, C::class)
}

/**
 * This is used for scoping an instance to a single thread.
 * Each test runs in its own thread but they can share the same process.
 * So, if an instance isn't scoped to a thread, a state from one test can leak into another test.
 * This class ensures that the instance is scoped to the thread that's running the test.
 *
 * I discovered ThreadLocal from the internal implementation of [android.os.Looper]
 * The need for state isolation between tests is learned from my experience at `Honest` Financial Technologies
* */
class ThreadScoped<T>(private val factory: () -> T) {
    private val threadLocal = ThreadLocal<T>()

    val instance: T
        get() = threadLocal.get() ?: factory().also { threadLocal.set(it) }

    fun set(value: T) {
        threadLocal.set(value)
    }

    fun clear() {
        threadLocal.remove()
    }
}
