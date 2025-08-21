package io.github.aungthiha.di

import io.github.aungthiha.di.core.testDouble
import io.github.aungthiha.myfeature.data.remote.MyfeatureDataSource
import io.github.aungthiha.myfeature.testdoubles.StubMyfeatureDataSource
import org.koin.dsl.module

val myfeatureOverrideModule = module {
    testDouble<MyfeatureDataSource, StubMyfeatureDataSource> {
        StubMyfeatureDataSource()
    }
}
