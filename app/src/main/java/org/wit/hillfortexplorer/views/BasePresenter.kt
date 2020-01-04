package org.wit.hillfortexplorer.views

import android.content.Intent
import android.view.MenuItem
import org.wit.hillfortexplorer.main.MainApp

open class BasePresenter(var view: BaseView?) {

    var app: MainApp = view?.application as MainApp

    fun doLogout() {
        view?.navigateTo(VIEW.AUTHENTICATION)
    }

    open fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    open fun doOptionsItemSelected(item: MenuItem) {

    }

    open fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

    }

    open fun onDestroy() {
        view = null
    }
}