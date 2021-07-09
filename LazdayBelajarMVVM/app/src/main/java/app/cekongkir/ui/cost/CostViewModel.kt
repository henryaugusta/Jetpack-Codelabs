package app.cekongkir.ui.cost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.database.preferences.PreferenceModel
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.network.Resource
import app.cekongkir.network.response.CityResponse
import app.cekongkir.network.response.CostResponse
import app.cekongkir.network.response.SubdistrictResponse
import kotlinx.coroutines.launch
import timber.log.Timber

class CostViewModel(val repository: RajaOngkirRepository) : ViewModel() {
    val preference : MutableLiveData<List<PreferenceModel>> = MutableLiveData()

    val costResponse : MutableLiveData<Resource<CostResponse>> = MutableLiveData()

    fun getPreference(){
        preference.value = repository.getPreference()
        costResponse.value = Resource.Loading()
    }

    fun fetchCost(
            origin: String, originType: String, destination: String,
            destinationType: String, weight: String, courier: String
    ) = viewModelScope.launch {
        costResponse.postValue(Resource.Loading())
        try {
            val response = repository.fetchCost( origin, originType, destination, destinationType, weight, courier)
            costResponse.postValue(Resource.Success(response.body()!!))
        } catch (e: Exception) {
            costResponse.postValue(Resource.Error(e.message.toString()))
            Timber.d("error cost : ${e.message.toString()}")
        }
    }





}