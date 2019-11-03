package org.wit.hillfortexplorer.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortexplorer.models.*

class MainApp : Application(), AnkoLogger {

    lateinit var users: UserStore
    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        info("Hillfort Explorer started")

        users = UserJSONStore(applicationContext)
        hillforts = HillfortJSONStore(applicationContext)
    }
}