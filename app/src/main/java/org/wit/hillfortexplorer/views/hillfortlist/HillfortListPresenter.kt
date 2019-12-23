package org.wit.hillfortexplorer.views.hillfortlist

import android.content.Intent
import android.view.MenuItem
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.views.hillfort.HillfortView
import org.wit.hillfortexplorer.views.hillfortsmap.HillfortsMapView
import org.wit.hillfortexplorer.views.settings.SettingsView
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.views.authentication.AuthenticationView

class HillfortListPresenter(val view: HillfortListActivity) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doLoadHillforts() {
        view.showHillforts(app.hillforts.findAll(app.currentUser.id))
    }

    fun doOptionsItemSelected(item: MenuItem) {
        when (item?.itemId) {
            R.id.item_add -> view.startActivityForResult<HillfortView>(0)
            R.id.item_map -> view.startActivity<HillfortsMapView>()
            R.id.item_settings -> view.startActivity<SettingsView>()
            R.id.item_logout -> view.startActivity<AuthenticationView>()
        }
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        doLoadHillforts()
    }
}