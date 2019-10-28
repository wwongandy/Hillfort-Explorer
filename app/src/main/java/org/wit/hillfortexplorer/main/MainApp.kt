package org.wit.hillfortexplorer.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortexplorer.models.HillfortModel

class MainApp : Application(), AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun onCreate() {
        super.onCreate()
        info("Hillfort Explorer started")

        hillforts.add(HillfortModel("Hillfort 1", "The first hillfort found"))
        hillforts.add(HillfortModel("Hillfort 2", "The second hillfort found"))
    }
}