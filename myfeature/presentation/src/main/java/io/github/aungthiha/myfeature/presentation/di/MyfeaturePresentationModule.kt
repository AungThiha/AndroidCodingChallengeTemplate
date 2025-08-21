package io.github.aungthiha.myfeature.presentation.di

import io.github.aungthiha.myfeature.domain.MyfeatureRepository
import io.github.aungthiha.myfeature.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val myfeaturePresentationModule = module {
    viewModel {
        HomeViewModel(
            getPokemons = get<MyfeatureRepository>().getPokemons
        )
    }
}
