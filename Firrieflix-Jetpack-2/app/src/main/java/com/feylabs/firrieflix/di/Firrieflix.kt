package com.feylabs.firrieflix.di

import android.app.Application
import com.feylabs.firrieflix.data.FirrieflixRepository
import com.feylabs.firrieflix.data.source.remote.RemoteDataSource
import com.feylabs.firrieflix.util.networking.ApiConfig
import com.feylabs.firrieflix.util.networking.FirrieflixEndpoint
import com.feylabs.firrieflix.viewmodel.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class Firrieflix : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@Firrieflix))

        bind() from singleton { RemoteDataSource() }
        bind<FirrieflixEndpoint>() with singleton { ApiConfig.getClient() }
        bind() from provider { FirrieflixRepository(instance()) }
        bind() from provider {ViewModelFactory(instance())}

    }

}