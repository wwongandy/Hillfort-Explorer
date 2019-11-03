package org.wit.hillfortexplorer.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp

class SettingsActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        app = application as MainApp

        toolbarSettings.title = title
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val _username = app.currentUser.username
        val _password = app.currentUser.password
        changeUsername.setOnClickListener {
            val oldUsername = oldUsername.text.toString()
            val newUsername = newUsername.text.toString()

            if (!sameCredentials(oldUsername, _username)) {
                toast(R.string.invalid_oldUsername)
            } else if (newUsername.isEmpty()) {
                toast(R.string.invalid_newUsername)
            } else {
                toast(R.string.valid_changeUsername)
            }
        }

        changePassword.setOnClickListener {
            val oldPassword = oldPassword.text.toString()
            val newPassword = newPassword.text.toString()

            if (!sameCredentials(oldPassword, _password)) {
                toast(R.string.invalid_oldPassword)
            } else if (newPassword.isEmpty()) {
                toast(R.string.invalid_newPassword)
            } else {
                toast(R.string.valid_changePassword)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_logout -> startActivity(intentFor<AuthenticationActivity>())
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun sameCredentials(first: String, second: String): Boolean {
        return first.equals(second)
    }
}