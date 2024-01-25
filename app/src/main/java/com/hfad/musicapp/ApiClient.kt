package com.hfad.musicapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {


    private lateinit var retrofitClient: Retrofit
    fun getApiService(): ApiMusicInterface {

        if (!this::retrofitClient.isInitialized) {
            val client = OkHttpClient().newBuilder().addInterceptor {
                it.proceed(
                    it.request()
                        .newBuilder()
                        .addHeader(
                            "X-RapidAPI-Key",
                            "282281eb54msh765060d1b44b8dcp10b621jsn0fc530465aef"
                        )
                        .addHeader("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
                        .build()
                )
            }.build()

            retrofitClient = Retrofit.Builder()
                .client(client)
                .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofitClient.create(ApiMusicInterface::class.java)
    }
}