package org.wit.hillfortexplorer.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortexplorer.models.HillfortMemStore
import org.wit.hillfortexplorer.models.HillfortModel

class MainApp : Application(), AnkoLogger {

    val hillforts = HillfortMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Hillfort Explorer started")

        hillforts.create(HillfortModel(9001, "Hillfort 1", "The first hillfort found"))
        hillforts.create(HillfortModel(9002, "Hillfort 2", "The second hillfort found"))
    }
}