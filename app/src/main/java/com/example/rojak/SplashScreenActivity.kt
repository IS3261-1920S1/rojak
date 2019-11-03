package com.example.rojak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.rojak.activities.home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_TIMEOUT: Long = 1500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val gotoHome: Intent = Intent(this, HomeActivity::class.java)
            startActivity(gotoHome)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, SPLASH_TIMEOUT)
    }
}
