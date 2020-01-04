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

        init(toolbarSettings, true)

        setUserStatistics(presenter.getUserStatistics())

        changeUsername.setOnClickListener {
            val newUsername = newUsername.text.toString()
            presenter.doChangeUsername(newUsername)
        }

        changePassword.setOnClickListener {
            val newPassword = newPassword.text.toString()
            presenter.doChangePassword(newPassword)
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

    fun setUserStatistics(statistics: HillfortUserStats) {
        statsView.setText(
            "Total number of hillforts: ${statistics.totalNumberOfHillforts}\n" +
            "Number of hillforts visited: ${statistics.visitedHillforts}\n" +
            "Visited this year: ${statistics.visitedThisYear}\n" +
            "Visited this month: ${statistics.visitedThisMonth}"
        )
    }
}