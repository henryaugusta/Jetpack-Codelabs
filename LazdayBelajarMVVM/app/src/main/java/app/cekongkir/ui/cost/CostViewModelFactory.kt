package app.cekongkir.ui.cost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.cekongkir.network.ApiService
import app.cekongkir.network.RajaOngkirEndpoint
import app.cekongkir.network.RajaOngkirRepository

//Repository Pattern dipisah berdasarkan Asal API

class CostViewModelFactory (
        private val repository: RajaOngkirRepository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CostViewModel(repository) as T
    }

}

//class CityViewModelFactory (
//        private val api:RajaOngkirEndpoint
//) : ViewModelProvider.Factory{
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return CityViewModel(api) as T
//    }
//
//}