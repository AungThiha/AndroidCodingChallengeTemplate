package io.github.aungthiha.codingchallengetemplate.di

import io.github.aungthiha.myfeature.data.di.myfeatureDataModule
import io.github.aungthiha.myfeature.presentation.di.myfeaturePresentationModule
import io.github.aungthiha.navigation.di.navigationModule
import io.github.aungthiha.network.di.networkModule
import org.koin.core.module.Module

val appModule : List<Module> = listOf(
    navigationModule,
    networkModule,

    myfeatureDataModule,
    myfeaturePresentationModule,


)
