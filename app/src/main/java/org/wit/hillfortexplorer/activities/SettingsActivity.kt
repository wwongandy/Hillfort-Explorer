package org.wit.hillfortexplorer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp

class SettingsActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        app = application as MainApp
    }
}