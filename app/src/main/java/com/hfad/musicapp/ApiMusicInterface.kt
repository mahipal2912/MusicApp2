package com.hfad.musicapp

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiMusicInterface {


   @GET("search")
   suspend fun getData(@Query("q")query:String): Response<MusicData>
}