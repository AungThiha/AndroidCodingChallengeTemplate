package io.github.aungthiha.network.retrofit2

import io.github.aungthiha.network.json.JsonProvider
import io.github.aungthiha.network.okhttp3.MEDIA_TYPE_JSON
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val DEFAULT_BASE_URL = "https://pokeapi.co/api/v2/"

/**
 * Naming a function with a capital letter is a convention in Kotlin to indicate that it's a factory function.
 * Take CoroutineScope function for example. It looks like a class but it's actually a function. (Of course, there's also CoroutineScope interface)
 * There's also MutableSharedFlow function with a capital letter. (Of course, there's also MutableSharedFlow interface)
 * This pattern is discouraged only in Java, not in Kotlin. In Kotlin, it's a common practice.
 * */
fun Retrofit(
    okHttpClient: OkHttpClient,
    callAdapterFactories: List<CallAdapter.Factory> = emptyList(),
    converterFactories: List<Converter.Factory> = listOf(
        JsonProvider.json.asConverterFactory(
            MEDIA_TYPE_JSON
        )
    ),
    baseUrl: String = DEFAULT_BASE_URL,
) : Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .apply {
        converterFactories.forEach(::addConverterFactory)
        callAdapterFactories.forEach(::addCallAdapterFactory)
    }
    .baseUrl(baseUrl)
    .build()
