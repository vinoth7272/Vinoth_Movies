package com.example.movierating

import android.app.Application
import android.content.Context
import com.example.movierating.data.network.networkModule
import com.example.movierating.viewmodel.viewModelModule
import org.kodein.di.*

class MovieApplication : Application(), DIAware {

    override val di = DI.lazy {
        bind<Context>("ApplicationContext") with singleton { this@MovieApplication.applicationContext }
        bind<MovieApplication>() with singleton { this@MovieApplication }
        import(networkModule)
        import(viewModelModule)
    }
}
