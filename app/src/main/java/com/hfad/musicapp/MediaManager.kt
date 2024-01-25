package com.hfad.musicapp

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.Toast

object MediaManager {

    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var currentMusicUri: String? = null

    suspend fun playSong(context: Context, uri: String) {
        if (currentMusicUri == uri && !mediaPlayer.isPlaying) {
            mediaPlayer.start()
            return
        }

        mediaPlayer.stop()
        mediaPlayer.reset()

        currentMusicUri = uri
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

        try {
            // on below line we are setting audio
            // source as audio url on below line.
            mediaPlayer.setDataSource(uri)

            // on below line we are
            // preparing our media player.
            mediaPlayer.prepare()

            // on below line we are
            // starting our media player.
            mediaPlayer.start()

        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Unable to play due to ${e.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
            // on below line we are handling our exception.
            e.printStackTrace()
        }
        // on below line we are displaying a toast message as audio player.
        Toast.makeText(context, "Music started playing..", Toast.LENGTH_SHORT).show()


    }

    fun pauseSong() {
        mediaPlayer.pause()
    }


}