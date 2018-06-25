package com.example.hendratay.kenneymainmenu2d

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseAcitivty() {

    private lateinit var soundPool: SoundPool
    private var clickSound: Int? = null

    companion object {
        val RC_SIGN_IN: Int = 123
    }

    val providers: List<AuthUI.IdpConfig> = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.TwitterBuilder().build()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupMultiplayerButton()
        setupOptionsButton()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = SoundPool.Builder().setMaxStreams(1).build()
            clickSound = soundPool.load(this, R.raw.click1, 1)
        }
    }

    fun playSound() {
        if(clickSound != null) {
            soundPool.play(clickSound!!, 1f, 1f, 0, 0, 1f)
        }
    }

    private fun setupMultiplayerButton() {
        multiplayer.setOnClickListener {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setLogo(R.drawable.zombie_duck)
                            .setTheme(R.style.SignInTheme)
                            .setTosUrl("https://github.com")
                            .setPrivacyPolicyUrl("https://google.com")
                            .build(),
                    RC_SIGN_IN
            )
        }
    }

    private fun setupOptionsButton() {
        options.setOnClickListener {
            playSound()
            startActivity(Intent(this, OptionsActivity::class.java))
        }
    }

}
