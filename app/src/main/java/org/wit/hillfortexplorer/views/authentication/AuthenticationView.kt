package org.wit.hillfortexplorer.views.authentication

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_authentication.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.views.BaseView

class AuthenticationView: BaseView(), AnkoLogger {

    lateinit var presenter: AuthenticationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        presenter = (initPresenter(AuthenticationPresenter(this))) as AuthenticationPresenter

        registerUser.setOnClickListener {
            val _username = username.text.toString()
            val _password = password.text.toString()

            if (_username.isEmpty() || _password.isEmpty()) {
                toast(R.string.enter_user_pass)
            } else {
                presenter.doRegisterUser(_username, _password)

                if (presenter.validRegistration) {
                    toast(R.string.valid_registration)
                } else {
                    toast(R.string.invalid_registration)
                }
            }
        }

        loginUser.setOnClickListener {
            presenter.doLoginUser(username.text.toString(), password.text.toString())

            if (!presenter.validLogin) {
                toast(R.string.invalid_login)
            }
        }
    }
}