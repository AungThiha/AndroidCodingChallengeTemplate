package io.github.aungthiha.network.di

import io.github.aungthiha.network.BuildConfig
import io.github.aungthiha.network.okhttp3.OkHttpClient
import io.github.aungthiha.network.retrofit2.Retrofit
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
const val DEFAULT_RETROFIT = "DEFAULT_RETROFIT"
const val AUTHENTICATED_RETROFIT = "AUTHENTICATED_RETROFIT"
const val AUTHORIZATION_INTERCEPTOR = "AUTHORIZATION_INTERCEPTOR"
val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        }
    }
    factory(named(DEFAULT_RETROFIT)) {
        Retrofit(
            okHttpClient = OkHttpClient(
                interceptors = listOf(
                    get<HttpLoggingInterceptor>(),
                ),
            )
        )
    }

    factory(named(AUTHENTICATED_RETROFIT)) {
        Retrofit(
            okHttpClient = OkHttpClient(
                interceptors = listOf(
                    get<HttpLoggingInterceptor>(),
                    get<Interceptor>(named(AUTHORIZATION_INTERCEPTOR))
                ),
                authenticator = get<Authenticator>(),
            )
        )
    }
}
