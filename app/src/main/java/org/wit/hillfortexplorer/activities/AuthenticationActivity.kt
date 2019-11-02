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
                toast(R.string.valid_registration)
            } else {
                toast(R.string.enter_user_pass)
            }
        }

        loginUser.setOnClickListener {
            startActivity(intentFor<HillfortListActivity>())
        }
    }
}