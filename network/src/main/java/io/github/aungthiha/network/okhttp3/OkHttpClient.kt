package io.github.aungthiha.network.okhttp3

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * Naming a function with a capital letter is a convention in Kotlin to indicate that it's a factory function.
 * Take CoroutineScope function for example. It looks like a class but it's actually a function. (Of course, there's also CoroutineScope interface)
 * There's also MutableSharedFlow function with a capital letter. (Of course, there's also MutableSharedFlow interface)
 * This pattern is discouraged only in Java, not in Kotlin. In Kotlin, it's a common practice.
 * */
fun OkHttpClient(
    interceptors: List<Interceptor>,
    authenticator: Authenticator? = null,
) = OkHttpClient.Builder()
    .apply {
        interceptors.forEach(::addInterceptor)
        authenticator?.let(::authenticator)
    }
    .build()
