package org.wit.hillfortexplorer.views.hillfortsmap

import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView

class HillfortsMapPresenter(view: BaseView): BasePresenter(view) {

    init {
        app = view.application as MainApp
    }

    fun doLoadHillforts(): List<HillfortModel> {
        return app.hillforts.findAll()
    }
}