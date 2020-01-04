package org.wit.hillfortexplorer.main

import android.app.Application
import com.google.firebase.auth.FirebaseUser
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfortexplorer.models.*

class MainApp : Application(), AnkoLogger {

    lateinit var currentUser: FirebaseUser
    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        info("Hillfort Explorer started")

        hillforts = HillfortJSONStore(applicationContext)
    }
}