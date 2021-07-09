package com.feylabs.firrieflix.util.networking

import com.feylabs.firrieflix.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class ApiConfig {

    companion object {

        const val BASE_URL = BuildConfig.BaseURL
        const val TOKEN = BuildConfig.token
        const val IMAGE_BASE_PATH = "http://image.tmdb.org/t/p/w185/"
        const val IMAGE_POSTER_BASE_PATH = "http://image.tmdb.org/t/p/original/"

        fun setFlagImage(code: String): String {
            var usedCode = code
            if (code == "en") {
                usedCode = "gb"
            }
            return "https://www.countryflags.io/$usedCode/shiny/64.png"
        }

        fun getClient(): FirrieflixEndpoint {
            val logging = HttpLoggingInterceptor()

            if (BuildConfig.DEBUG) {
                // development build
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                // production build
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(Interceptor { chain ->
                    val original: Request = chain.request()
                    val originalHttpUrl: HttpUrl = original.url
                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", "2671c64cf6ce8c1265cf8ce1c3543bf9")
                        .build()
                    // Request customization: add request headers
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .url(url)
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                })
                .build()

            val myRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return myRetrofit.create(FirrieflixEndpoint::class.java)
        }

    }
}