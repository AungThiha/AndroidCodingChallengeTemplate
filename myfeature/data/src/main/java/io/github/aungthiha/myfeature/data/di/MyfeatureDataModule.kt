package io.github.aungthiha.myfeature.data.di

import io.github.aungthiha.myfeature.data.MyfeatureRepositoryImpl
import io.github.aungthiha.myfeature.data.remote.MyfeatureDataSource
import io.github.aungthiha.myfeature.domain.MyfeatureRepository
import io.github.aungthiha.network.di.DEFAULT_RETROFIT
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val myfeatureDataModule = module {
    factory<MyfeatureRepository> {
        MyfeatureRepositoryImpl(
            myfeatureDataSource = get()
        )
    }

    factory<MyfeatureDataSource> {
        get<Retrofit>(named(DEFAULT_RETROFIT))
            .create(MyfeatureDataSource::class.java)
    }
}
