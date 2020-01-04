package org.wit.hillfortexplorer.views.authentication

import android.os.Bundle
import android.view.View
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

            presenter.doRegisterUser(_username, _password)
        }

        loginUser.setOnClickListener {
            presenter.doLoginUser(username.text.toString(), password.text.toString())
        }

        progressBar.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}