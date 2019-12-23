package org.wit.hillfortexplorer.activities

import android.content.Intent
import android.view.MenuItem
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp

class HillfortListPresenter(val view: HillfortListActivity) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun loadHillforts() {
        view.showHillforts(app.hillforts.findAll(app.currentUser.id))
    }

    fun doOptionsItemSelected(item: MenuItem) {
        when (item?.itemId) {
            R.id.item_add -> view.startActivityForResult<HillfortActivity>(0)
            R.id.item_map -> view.startActivity<HillfortsMapActivity>()
            R.id.item_settings -> view.startActivity<SettingsActivity>()
            R.id.item_logout -> view.startActivity<AuthenticationActivity>()
        }
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
    }
}