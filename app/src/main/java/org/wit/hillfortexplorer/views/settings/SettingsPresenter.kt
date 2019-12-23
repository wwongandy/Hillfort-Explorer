package org.wit.hillfortexplorer.views.settings

import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.intentFor
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.views.authentication.AuthenticationView

class SettingsPresenter(val view: SettingsView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doInitStatsView() {
        val statistics = app.hillforts.getUserStatistics(app.currentUser.id)

        view.statsView.setText(
            "Total number of hillforts: ${statistics.totalNumberOfHillforts}\n" +
            "Number of hillforts visited: ${statistics.visitedHillforts}\n" +
            "Visited this year: ${statistics.visitedThisYear}\n" +
            "Visited this month: ${statistics.visitedThisMonth}"
        )
    }

    fun doChangeUsername() {
        val _username = app.currentUser.username
        val _password = app.currentUser.password
        val oldUsername = view.oldUsername.text.toString()
        val newUsername = view.newUsername.text.toString()

        if (!sameCredentials(oldUsername, _username)) {
            view.notifyInvalidOldUsername()
        } else if (newUsername.isEmpty()) {
            view.notifiyInvalidNewUsername()
        } else if (!app.users.ensureUniqueCredentials(newUsername, _password)) {
            view.notifyDuplicateUsername()
        } else {
            app.users.changeUsername(_username, _password, newUsername)
            app.currentUser = app.users.authenticate(newUsername, _password)

            view.notifiyUsernameChanged()
        }
    }

    fun doChangePassword() {
        val _username = app.currentUser.username
        val _password = app.currentUser.password
        val oldPassword = view.oldPassword.text.toString()
        val newPassword = view.newPassword.text.toString()

        if (!sameCredentials(oldPassword, _password)) {
            view.notifiyInvalidOldPassword()
        } else if (newPassword.isEmpty()) {
            view.notifyInvalidNewPassword()
        } else {
            app.users.changePassword(_username, _password, newPassword)
            app.currentUser = app.users.authenticate(_username, newPassword)

            view.notifyPasswordChanged()
        }
    }

    fun doOptionsItemSelected(item: MenuItem) {
        when (item?.itemId) {
            R.id.item_logout -> view.startActivity(view.intentFor<AuthenticationView>())
        }
    }

    private fun sameCredentials(first: String, second: String): Boolean {
        return first.equals(second)
    }
}