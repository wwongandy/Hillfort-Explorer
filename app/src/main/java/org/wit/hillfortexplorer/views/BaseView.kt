package org.wit.hillfortexplorer.views

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.views.authentication.AuthenticationView
import org.wit.hillfortexplorer.views.editlocation.EditLocationView
import org.wit.hillfortexplorer.views.hillfort.HillfortView
import org.wit.hillfortexplorer.views.hillfortlist.HillfortListActivity
import org.wit.hillfortexplorer.views.hillfortsmap.HillfortsMapView
import org.wit.hillfortexplorer.views.settings.SettingsView
import org.wit.hillfortexplorer.views.splashscreen.SplashScreenView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
    AUTHENTICATION, EDITLOCATION, HILLFORT, HILLFORTLIST, HILLFORTSMAP, SETTINGS, SPLASHSCREEN
}

open abstract class BaseView(): AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter ?= null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable ?= null) {
        var intent = Intent(this, HillfortListActivity::class.java)

        when(view) {
            VIEW.AUTHENTICATION  -> intent = Intent(this, AuthenticationView::class.java)
            VIEW.EDITLOCATION  -> intent = Intent(this, EditLocationView::class.java)
            VIEW.HILLFORT  -> intent = Intent(this, HillfortView::class.java)
            VIEW.HILLFORTLIST -> intent = Intent(this, HillfortListActivity::class.java)
            VIEW.HILLFORTSMAP  -> intent = Intent(this, HillfortsMapView::class.java)
            VIEW.SETTINGS -> intent = Intent(this, SettingsView::class.java)
            VIEW.SPLASHSCREEN -> intent = Intent(this, SplashScreenView::class.java)
        }

        if (key != "") {
            intent.putExtra(key, value)
        }

        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar) {
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showHillfort(hillfort: HillfortModel) {}
    open fun showHillforts(hillforts: List<HillfortModel>) {}
    open fun showProgress() {}
    open fun hideProgress() {}
}