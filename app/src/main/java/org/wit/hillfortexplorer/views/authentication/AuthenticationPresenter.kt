package org.wit.hillfortexplorer.views.authentication

import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.VIEW

class AuthenticationPresenter(view: BaseView): BasePresenter(view) {

    var validRegistration: Boolean = false
    var validLogin: Boolean = false

    init {
        app = view.application as MainApp
    }

    fun doRegisterUser(username: String, password: String) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            validRegistration = app.users.register(username, password)
        }
    }

    fun doLoginUser(username: String, password: String) {
        app.currentUser = app.users.authenticate(username, password)
        if (app.currentUser.username.isNotEmpty()) {
            validLogin = true

            view?.navigateTo(VIEW.HILLFORTLIST)
        }
    }
}