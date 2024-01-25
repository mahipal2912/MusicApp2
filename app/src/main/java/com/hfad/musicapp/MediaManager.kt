package com.hfad.musicapp

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MediaManager {

    private val mediaPlayer by lazy { MediaPlayer() }
    private var currentMusicUri: String? = null

    suspend fun playSong(context: Context, uri: String) {
        withContext(Dispatchers.IO) {
            if (currentMusicUri == uri) {
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                }
                return@withContext
            }

            try {
                mediaPlayer.apply {
                    stop()
                    reset()
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(uri)
                    prepare()
                    start()
                }
                currentMusicUri = uri

                Toast.makeText(context, "Music started playing..", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Unable to play due to ${e.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    fun pauseSong() {
        mediaPlayer.pause()
    }
}
