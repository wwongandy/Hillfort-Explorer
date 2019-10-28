package org.wit.hillfortexplorer.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortexplorer.models.HillfortMemStore
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.HillfortStore

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        info("Hillfort Explorer started")

        hillforts = HillfortMemStore()

        hillforts.create(HillfortModel(9001, "Hillfort 1", "The first hillfort found"))
        hillforts.create(HillfortModel(9002, "Hillfort 2", "The second hillfort found"))
    }
}