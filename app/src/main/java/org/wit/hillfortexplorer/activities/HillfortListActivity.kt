package org.wit.hillfortexplorer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp

class HillfortListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp
    }
}
}
