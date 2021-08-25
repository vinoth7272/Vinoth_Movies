package com.example.movierating.viewmodel

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider

val viewModelModule = DI.Module(name = "viewModel") {
    bind<MovieViewModel>() with provider {
        MovieViewModel(instance())
    }
}
