package org.wit.hillfortexplorer.views.settings

import android.view.MenuItem
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortUserStats
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.VIEW

class SettingsPresenter(view: BaseView): BasePresenter(view) {

    var usernameChanged: Boolean = false
    var passwordChanged: Boolean = false

    init {
        app = view.application as MainApp
    }

    fun getUserStatistics(): HillfortUserStats {
        return app.hillforts.getUserStatistics(app.currentUser.id)
    }

    fun doChangeUsername(newUsername: String) {
        val _username = app.currentUser.username
        val _password = app.currentUser.password

        app.users.changeUsername(_username, _password, newUsername)
        app.currentUser = app.users.authenticate(newUsername, _password)
        usernameChanged = true
    }

    fun doChangePassword(newPassword: String) {
        val _username = app.currentUser.username
        val _password = app.currentUser.password

        app.users.changePassword(_username, _password, newPassword)
        app.currentUser = app.users.authenticate(_username, newPassword)
        passwordChanged = true
    }

    override fun doOptionsItemSelected(item: MenuItem) {
        when (item?.itemId) {
            R.id.item_logout -> view?.navigateTo(VIEW.AUTHENTICATION)
        }
    }

    private fun sameCredentials(first: String, second: String): Boolean {
        return first.equals(second)
    }

    fun checkCorrectOldUsername(oldPassword: String): Boolean {
        return sameCredentials(app.currentUser.username, oldPassword)
    }

    fun checkCorrectOldPassword(oldPassword: String): Boolean {
        return sameCredentials(app.currentUser.password, oldPassword)
    }

    fun checkIsDuplicateUsername(newUsername: String): Boolean {
        return app.users.ensureUniqueCredentials(newUsername, app.currentUser.password)
    }
}