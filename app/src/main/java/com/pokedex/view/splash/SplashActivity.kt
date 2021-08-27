package com.pokedex.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pokedex.R
import com.pokedex.utils.extensions.launchWithDelay
import com.pokedex.view.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToMainActivity()
    }

    private fun goToMainActivity() {
        lifecycleScope.launchWithDelay(400) {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}