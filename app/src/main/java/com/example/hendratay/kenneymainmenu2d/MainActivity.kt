package com.example.hendratay.kenneymainmenu2d

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
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

}
