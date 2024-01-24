package com.hfad.musicapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiMusicInterface {


    @Headers("X-RapidAPI-Key:282281eb54msh765060d1b44b8dcp10b621jsn0fc530465aef"+
            "X-RapidAPI-Host:deezerdevs-deezer.p.rapidapi.com")
   @GET("search")
   fun getData(@Query("q")query:String): Call<MusicData>
}