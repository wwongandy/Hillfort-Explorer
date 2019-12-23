package org.wit.hillfortexplorer.activities

import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel

class HillfortsMapPresenter(val view: HillfortsMapActivity) {

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