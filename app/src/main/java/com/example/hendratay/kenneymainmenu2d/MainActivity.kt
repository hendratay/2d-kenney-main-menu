package com.example.hendratay.kenneymainmenu2d

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool
    private var user: FirebaseUser? = null
    private var clickSound: Int? = null
    private var soundEffectVolume = 0

    companion object {
        const val RC_SIGN_IN: Int = 123
    }

    private val providers: List<AuthUI.IdpConfig> = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build()
//            AuthUI.IdpConfig.TwitterBuilder().build()
    )

    var backgroundMusic: BackgroundMusic? = null
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
        setContentView(R.layout.activity_main)
        bindService(Intent(this, BackgroundMusic::class.java), serviceConnection, Context.BIND_AUTO_CREATE)

        setupSinglePlayerButton()
        setupMultiPlayerButton()
        setupOptionsButton()
        setupInviteButton()
        setupAchievementButton()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = SoundPool.Builder().setMaxStreams(1).build()
        } else {
            soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }
        clickSound = soundPool.load(this, R.raw.click1, 1)
        readSoundEffectPref()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN) {
            if(resultCode == Activity.RESULT_OK) {
                user = FirebaseAuth.getInstance().currentUser
                startActivity(Intent(this, DashBoardActivity::class.java).putExtra("USERNAME", user?.displayName))
            }
        }
    }

    private fun readSoundEffectPref() {
        val sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        soundEffectVolume = sharedPreferences.getInt(getString(R.string.saved_sound_effect), 50)
    }

    private fun playSound() {
        if(clickSound != null) {
            soundPool.play(clickSound!!, soundEffectVolume / 100f , soundEffectVolume / 100f, 0, 0, 1f)
        }
    }

    fun setSoundEffectVolume(volume: Int) {
        soundEffectVolume = volume
    }

    private fun setupSinglePlayerButton() {
        single_player.setOnClickListener {
            playSound()
        }
    }

    private fun setupMultiPlayerButton() {
        multiplayer.setOnClickListener {
            playSound()
            user = FirebaseAuth.getInstance().currentUser
            if(user != null) {
                startActivity(Intent(this, DashBoardActivity::class.java).putExtra("USERNAME", user!!.displayName))
            } else {
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
    }

    private fun setupOptionsButton() {
        options.setOnClickListener {
            playSound()
            OptionsDialog().show(supportFragmentManager, "OptionsDialog")
        }
    }

    private fun setupInviteButton() {
        invite.setOnClickListener {
            playSound()
            val sendIntent = Intent().apply {
                setAction(Intent.ACTION_SEND)
                setType("text/plain")
                putExtra(Intent.EXTRA_TEXT, "//Todo: use dynamic links")
            }
            startActivity(sendIntent)
        }
    }

    private fun setupAchievementButton() {
        achievement.setOnClickListener {
            playSound()
        }
    }

}
