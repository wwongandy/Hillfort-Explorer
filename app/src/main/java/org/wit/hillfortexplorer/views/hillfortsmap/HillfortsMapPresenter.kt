package org.wit.hillfortexplorer.views.hillfortsmap

import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel

class HillfortsMapPresenter(val view: HillfortsMapView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun doLoadHillforts(): List<HillfortModel> {
        return app.hillforts.findAll(app.currentUser.id)
    }

    fun doGetHillfort(tag: Long): HillfortModel? {
        return app.hillforts.findById(tag)
    }
}