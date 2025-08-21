package io.github.aungthiha.navigation.di

import io.github.aungthiha.navigation.DefaultNavigationDispatcher
import io.github.aungthiha.navigation.NavigationDispatcher
import org.koin.dsl.module

val navigationModule = module {
    single<NavigationDispatcher> { DefaultNavigationDispatcher() }
}
