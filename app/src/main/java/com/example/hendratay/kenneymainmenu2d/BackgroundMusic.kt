package com.example.hendratay.kenneymainmenu2d

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class BackgroundMusic: Service() {

    private val binder: IBinder = ServiceBinder()
    private lateinit var mediaPlayer: MediaPlayer
    private var length = 0

    inner class ServiceBinder: Binder() {
        fun getService(): BackgroundMusic {
            return this@BackgroundMusic
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.clearday)
        readBackgroundMusicPref()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    fun setMusicVolume(volume: Float) {
        mediaPlayer.setVolume(volume, volume)
    }

    fun pauseMusic() {
        mediaPlayer.pause()
        length = mediaPlayer.currentPosition
    }

    private fun readBackgroundMusicPref() {
        val sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        val musicVolume = sharedPreferences.getInt(getString(R.string.saved_music_volume), 50)
        setMusicVolume(musicVolume / 100f)
    }


}