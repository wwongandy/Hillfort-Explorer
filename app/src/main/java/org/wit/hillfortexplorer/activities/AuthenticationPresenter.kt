package org.wit.hillfortexplorer.activities

import kotlinx.android.synthetic.main.activity_authentication.*
import org.jetbrains.anko.intentFor
import org.wit.hillfortexplorer.main.MainApp

class AuthenticationPresenter(val view: AuthenticationActivity) {

    var app : MainApp

    init {
        app = view.application as MainApp
    }

    fun doRegisterUser() {
        val username = view.username.text.toString()
        val password = view.password.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            val validRegistration = app.users.register(username, password)
            view.notifiyRegistrationSuccess(validRegistration)
        } else {
            view.notifyEnterUsernameAndPassword()
        }
    }

    fun doLoginUser() {
        val username = view.username.text.toString()
        val password = view.password.text.toString()

        app.currentUser = app.users.authenticate(username, password)
        if (app.currentUser.username.isNotEmpty()) {
            view.startActivity(view.intentFor<HillfortListActivity>())
        } else {
            view.notifyInvalidLogin()
        }
    }
}