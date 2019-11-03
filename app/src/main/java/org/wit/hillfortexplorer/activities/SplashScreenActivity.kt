package org.wit.hillfortexplorer.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.wit.hillfortexplorer.R

class SplashScreenActivity: AppCompatActivity() {

    val SPLASHSCREEN_DISPLAY_LENGTH = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed(Runnable {
            run {
                startActivity(intentFor<AuthenticationActivity>())
            }
        }, SPLASHSCREEN_DISPLAY_LENGTH.toLong())
    }
}