package org.wit.hillfortexplorer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authentication.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp

class AuthenticationActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        app = application as MainApp

        registerUser.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val validRegistration = app.users.register(username, password)

                if (validRegistration) {
                    toast(R.string.valid_registration)
                } else {
                    toast(R.string.invalid_registration)
                }
            } else {
                toast(R.string.enter_user_pass)
            }
        }

        loginUser.setOnClickListener {

            val username = username.text.toString()
            val password = password.text.toString()

            app.currentUser = app.users.authenticate(username, password)
            if (app.currentUser.username.isNotEmpty()) {
                startActivity(intentFor<HillfortListActivity>())
            } else {
                toast(R.string.invalid_login)
            }
        }
    }
}