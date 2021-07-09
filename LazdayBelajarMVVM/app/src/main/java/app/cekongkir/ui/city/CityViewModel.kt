package app.cekongkir.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cekongkir.network.RajaOngkirRepository
import app.cekongkir.network.Resource
import app.cekongkir.network.response.CityResponse
import app.cekongkir.network.response.SubdistrictResponse
import kotlinx.coroutines.launch

class CityViewModel(val repository: RajaOngkirRepository) : ViewModel() {
    val titleBar: MutableLiveData<String> = MutableLiveData("")
    val cityResponse : MutableLiveData<Resource<CityResponse>> = MutableLiveData()
    val subDistrictResponse : MutableLiveData<Resource<SubdistrictResponse>> = MutableLiveData()

    init{
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            cityResponse.value = Resource.Loading()
            try {
                val response = repository.fetchCity().body()!!
                cityResponse.value=Resource.Success(response)
            }catch (e:Exception){
                cityResponse.value=Resource.Error(e.message.toString())
            }
        }
    }

    fun fetchSubdistrict(id:String) {
        viewModelScope.launch {
            subDistrictResponse.value = Resource.Loading()
            try {
                val response = repository.fetchSubDistrict(id).body()!!
                subDistrictResponse.value=Resource.Success(response)
            }catch (e:Exception){
                subDistrictResponse.value=Resource.Error(e.message.toString())
            }
        }
    }

    fun savePreferences (type: String, id: String, name: String) {
        repository.savePreference( type, id, name )
    }




}