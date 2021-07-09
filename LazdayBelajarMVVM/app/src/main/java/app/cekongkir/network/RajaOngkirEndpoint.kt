package app.cekongkir.network

import app.cekongkir.network.response.CityResponse
import app.cekongkir.network.response.CostResponse
import app.cekongkir.network.response.SubdistrictResponse
import app.cekongkir.network.response.WaybillResponse
import retrofit2.Response
import retrofit2.http.*

interface RajaOngkirEndpoint {

    //tambahkan Interface ini ke ApiService

    //ket :  /City menyesuaikan dengan path api setelah base URL
    @GET("api/city")
    suspend fun city() : Response<CityResponse>

    //ket :  /City menyesuaikan dengan path api setelah base URL
    @GET("subdistrict")
    suspend fun subdistrict(
            @Query("city") cityID : String
    ) : Response<SubdistrictResponse>

    @FormUrlEncoded
    @POST("cost")
    suspend fun cost(
            @Field("origin") origin: String,
            @Field("originType") originType: String,
            @Field("destination") destination: String,
            @Field("destinationType") destinationType: String,
            @Field("weight") weight: String,
            @Field("courier") courier: String
    ) : Response<CostResponse>

    @FormUrlEncoded
    @POST("waybill")
    suspend fun waybill(
            @Field("waybill") waybill: String,
            @Field("courier") courier: String
    ) : Response<WaybillResponse>



}