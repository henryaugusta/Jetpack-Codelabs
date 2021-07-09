package app.cekongkir.network

import app.cekongkir.database.preferences.CekOngkirPreference
import app.cekongkir.database.preferences.PrefConst.PREF_DESTINATION_ID
import app.cekongkir.database.preferences.PrefConst.PREF_DESTINATION_NAME
import app.cekongkir.database.preferences.PrefConst.PREF_ORIGIN_ID
import app.cekongkir.database.preferences.PrefConst.PREF_ORIGIN_NAME
import app.cekongkir.database.preferences.PreferenceModel
import app.cekongkir.network.response.CityResponse
import retrofit2.Response

class RajaOngkirRepository(
        private val api: RajaOngkirEndpoint,
        private val pref: CekOngkirPreference,
) {

    suspend fun fetchCity(): Response<CityResponse> = api.city()
    suspend fun fetchSubDistrict(cityID: String) = api.subdistrict(cityID)

    fun savePreference(type: String, id: String, name: String) {
        when (type) {
            "origin" -> {
                pref.put(PREF_ORIGIN_ID, id)
                pref.put(PREF_ORIGIN_NAME, name)
            }
            "destination" -> {
                pref.put(PREF_DESTINATION_ID, id)
                pref.put(PREF_DESTINATION_NAME, name)
            }
        }
    }

    fun getPreference(): List<PreferenceModel> {
        return listOf(
                PreferenceModel(
                        type = "origin",
                        id = pref.getString(PREF_ORIGIN_ID).toString(),
                        name = pref.getString(PREF_ORIGIN_NAME).toString()),
                PreferenceModel(
                        type = "destination",
                        id = pref.getString(PREF_DESTINATION_ID).toString(),
                        name = pref.getString(PREF_DESTINATION_NAME).toString()),
        )
    }

    suspend fun fetchCost(
            origin: String, originType: String, destination: String,
            destinationType: String, weight: String, courier: String) =
            api.cost(origin, originType, destination, destinationType, weight, courier)

    suspend fun fetchWaybill(
            waybill: String, courier: String
    ) = api.waybill(waybill, courier)

}