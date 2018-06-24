package com.example.hendratay.kenneymainmenu2d

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity

open class BaseAcitivty: AppCompatActivity() {

    private var backgroundMusic: BackgroundMusic? = null
    private val serviceConnection: ServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            backgroundMusic = (service as BackgroundMusic.ServiceBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            backgroundMusic = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindService(Intent(this, BackgroundMusic::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        startService(Intent(this, BackgroundMusic::class.java))
    }

    override fun onPause() {
        super.onPause()
        backgroundMusic?.pauseMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, BackgroundMusic::class.java))
        unbindService(serviceConnection)
    }

}