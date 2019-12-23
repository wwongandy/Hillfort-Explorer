package org.wit.hillfortexplorer.views.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R

class SettingsView: AppCompatActivity(), AnkoLogger {

    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        presenter = SettingsPresenter(this)

        toolbarSettings.title = title
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.doInitStatsView()

        changeUsername.setOnClickListener {
            presenter.doChangeUsername()
        }

        changePassword.setOnClickListener {
            presenter.doChangePassword()
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
}