package org.wit.hillfortexplorer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import org.wit.hillfortexplorer.R

import kotlinx.android.synthetic.main.activity_hillforts_map.*
import kotlinx.android.synthetic.main.content_hillforts_map.*

class HillfortsMapActivity : AppCompatActivity() {

    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillforts_map)
        toolbar.title = title
        setSupportActionBar(toolbar)
        mapView.onCreate(savedInstanceState)
    }
}
