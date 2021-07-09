package app.cekongkir

import android.app.Application
import app.cekongkir.database.preferences.CekOngkirPreference
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirEndpoint
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.ui.city.CityViewModelFactory
import app.cekongkir.ui.cost.CostViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import timber.log.Timber

class MyApp : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MyApp))

        bind<RajaOngkirEndpoint>() with singleton { ApiService.getClient() }
        bind() from singleton { CekOngkirPreference(instance()) }
        bind() from singleton { RajaOngkirRepository(instance(),instance()) }
        bind() from singleton { CityViewModelFactory(instance()) }
        bind() from singleton { CostViewModelFactory(instance()) }
    }
}