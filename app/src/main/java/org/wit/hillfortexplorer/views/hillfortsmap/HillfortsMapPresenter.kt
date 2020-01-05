package org.wit.hillfortexplorer.views.hillfortsmap

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView

class HillfortsMapPresenter(view: BaseView): BasePresenter(view) {

    var map: GoogleMap ?= null

    init {
        app = view.application as MainApp
    }

    fun doConfigureMap(_map: GoogleMap) {
        map = _map
        map?.uiSettings?.isZoomControlsEnabled = true

        doAsync {
            val hillforts = app.hillforts.findAll()

            uiThread {
                hillforts.forEach {
                    val loc = LatLng(it.location.lat, it.location.lng)
                    val options = MarkerOptions().title(it.title).position(loc)
                    map?.addMarker(options)?.tag = it

                    // Move camera to most recent Hillfort
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
                }
            }
        }
    }
}