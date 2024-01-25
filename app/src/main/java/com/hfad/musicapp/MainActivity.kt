package com.hfad.musicapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var myRecyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myRecyclerView = findViewById(R.id.rvMusic)

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

        val builder = Retrofit.Builder()
            .client(client)
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiMusicInterface::class.java)

        lifecycleScope.launch {
            val response = builder.getData("eminem")
            Log.e("Response", "FullResponse -> $response")

            if (response.isSuccessful) {

                response.body()?.let {

                    Log.e("Response", "FullResponse Data-> $it")

                    myAdapter = MyAdapter(it.data, playSong = { url ->
                        lifecycleScope.launch {
                            MediaManager.playSong(this@MainActivity, url)
                        }
                    }, stop = {
                        MediaManager.pauseSong()
                    })
                    myRecyclerView.adapter = myAdapter

                    myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                } ?: Log.e("Response", " Data is null ${response.body()}")

            } else {
                Log.e("Response", "Response is not successful ${response.errorBody()?.string()}")
            }
        }


    }
}