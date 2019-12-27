package org.wit.hillfortexplorer.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.models.HillfortUserStats
import org.wit.hillfortexplorer.views.BaseView

class SettingsView: BaseView(), AnkoLogger {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        presenter = (initPresenter(SettingsPresenter(this))) as SettingsPresenter

        init(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUserStatistics(presenter.getUserStatistics())

        changeUsername.setOnClickListener {
            val oldUsername = oldUsername.text.toString()
            val newUsername = newUsername.text.toString()

            if (!presenter.checkCorrectOldUsername(oldUsername)) {
                notifyInvalidOldUsername()
            } else if (newUsername.isEmpty()) {
                notifiyInvalidNewUsername()
            } else if (!presenter.checkIsDuplicateUsername(newUsername)) {
                notifyDuplicateUsername()
            } else {
                presenter.doChangeUsername(newUsername)
                notifiyUsernameChanged()
            }
        }

        changePassword.setOnClickListener {
            val oldPassword = oldPassword.text.toString()
            val newPassword = newPassword.text.toString()

            if (!presenter.checkCorrectOldPassword(oldPassword)) {
                notifiyInvalidOldPassword()
            } else if (newPassword.isEmpty()) {
                notifyInvalidNewPassword()
            } else {
                presenter.doChangePassword(newPassword)
                notifyPasswordChanged()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.doOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun notifyInvalidOldUsername() {
        toast(R.string.invalid_oldUsername)
    }

    fun notifiyInvalidNewUsername() {
        toast(R.string.invalid_newUsername)
    }

    fun notifyDuplicateUsername() {
        toast(R.string.duplicate_newUsername)
    }

    fun notifiyUsernameChanged() {
        toast(R.string.valid_changeUsername)
    }

    fun notifiyInvalidOldPassword() {
        toast(R.string.invalid_oldPassword)
    }

    fun notifyInvalidNewPassword() {
        toast(R.string.invalid_newPassword)
    }

    fun notifyPasswordChanged() {
        toast(R.string.valid_changePassword)
    }

    fun setUserStatistics(statistics: HillfortUserStats) {
        statsView.setText(
            "Total number of hillforts: ${statistics.totalNumberOfHillforts}\n" +
            "Number of hillforts visited: ${statistics.visitedHillforts}\n" +
            "Visited this year: ${statistics.visitedThisYear}\n" +
            "Visited this month: ${statistics.visitedThisMonth}"
        )
    }
}