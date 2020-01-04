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
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.VIEW
import org.wit.hillfortexplorer.views.authentication.AuthenticationView

class HillfortListPresenter(view: BaseView): BasePresenter(view) {

    init {
        app = view.application as MainApp
    }

    fun doLoadHillforts() {
        view?.showHillforts(app.hillforts.findAll(app.currentUser.uid))
    }

    override fun doOptionsItemSelected(item: MenuItem) {
        when (item?.itemId) {
            R.id.item_add -> view?.navigateTo(VIEW.HILLFORT, 0)
            R.id.item_map -> view?.navigateTo(VIEW.HILLFORTSMAP)
            R.id.item_settings -> view?.navigateTo(VIEW.SETTINGS)
            R.id.item_logout -> doLogout()
        }
    }
}