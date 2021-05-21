package com.example.ganesh.deep_android.multithreading.mvvmcoroutines.network

/**
 * Created by Ganesh Padhayay on 17/05/21.
 */
import com.example.ganesh.deep_android.multithreading.mvvmcoroutines.util.SkipNetworkInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val service: MainNetwork by lazy {
    val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(SkipNetworkInterceptor())
            .build()

    val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    retrofit.create(MainNetwork::class.java)
}

fun getNetworkService() = service

/**
 * Main network interface which will fetch a new welcome title for us
 */
interface MainNetwork {
    @GET("next_title.json")
    suspend fun fetchNextTitle(): String
}