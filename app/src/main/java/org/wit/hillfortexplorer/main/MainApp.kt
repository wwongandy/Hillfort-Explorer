package org.wit.hillfortexplorer.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortexplorer.models.HillfortJSONStore
import org.wit.hillfortexplorer.models.HillfortMemStore
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.HillfortStore

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        info("Hillfort Explorer started")

        hillforts = HillfortJSONStore(applicationContext)
    }
}