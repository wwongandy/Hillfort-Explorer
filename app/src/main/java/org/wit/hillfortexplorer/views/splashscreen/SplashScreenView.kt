package org.wit.hillfortexplorer.views.splashscreen

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.intentFor
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.VIEW
import org.wit.hillfortexplorer.views.authentication.AuthenticationView

class SplashScreenView: BaseView() {

    val SPLASHSCREEN_DISPLAY_LENGTH = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed(Runnable {
            run {
                navigateTo(VIEW.AUTHENTICATION)
            }
        }, SPLASHSCREEN_DISPLAY_LENGTH.toLong())
    }
}