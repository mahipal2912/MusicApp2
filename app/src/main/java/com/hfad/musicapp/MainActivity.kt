package com.hfad.musicapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.musicapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var myAdapter: MyAdapter

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apiClient = ApiClient.getApiService()

        lifecycleScope.launch {
            try {
                val response = apiClient.getData("eminem")
                handleApiResponse(response)
            } catch (e: Exception) {
                Log.e("Response", "Error: ${e.message}")
            }
        }
    }

    private fun handleApiResponse(response: Response<MusicData>) {
        if (response.isSuccessful) {
            response.body()?.let { data ->
                Log.e("Response", "FullResponse Data -> $data")
                setupRecyclerView(data)
            } ?: Log.e("Response", "Data is null ${response.body()}")
        } else {
            Log.e("Response", "Response is not successful ${response.errorBody()?.string()}")
        }
    }

    private fun setupRecyclerView(data: MusicData) {
        myAdapter = MyAdapter(data.data, playSong = { url ->
            lifecycleScope.launch {
                MediaManager.playSong(this@MainActivity, url)
            }
        }, stop = {
            MediaManager.pauseSong()
        })
        binding.rvMusic.adapter = myAdapter
        binding.rvMusic.layoutManager = LinearLayoutManager(this@MainActivity)
    }


}
