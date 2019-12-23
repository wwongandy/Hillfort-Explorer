package org.wit.hillfortexplorer.views.authentication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authentication.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R

class AuthenticationView: AppCompatActivity(), AnkoLogger {

    lateinit var presenter: AuthenticationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        presenter = AuthenticationPresenter(this)

        registerUser.setOnClickListener {
            presenter.doRegisterUser()
        }

        loginUser.setOnClickListener {
            presenter.doLoginUser()
        }
    }

    fun notifiyRegistrationSuccess(validRegistration: Boolean) {
        if (validRegistration) {
            toast(R.string.valid_registration)
        } else {
            toast(R.string.invalid_registration)
        }
    }

    fun notifyEnterUsernameAndPassword() {
        toast(R.string.enter_user_pass)
    }

    fun notifyInvalidLogin() {
        toast(R.string.invalid_login)
    }
}