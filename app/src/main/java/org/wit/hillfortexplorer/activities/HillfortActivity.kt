package org.wit.hillfortexplorer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        app = application as MainApp
        info("Hillfort Activity started..")

        btnAdd.setOnClickListener() {

            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()

            if (hillfort.title.isNotEmpty() && hillfort.description.isNotEmpty()) {

                app.hillforts.add(hillfort.copy())
                info("Add button pressed: $hillfort")
            } else {
                toast("Please provide a title and description for the Hillfort")
            }
        }
    }
}
