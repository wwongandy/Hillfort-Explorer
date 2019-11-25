package org.wit.hillfortexplorer.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import org.wit.hillfortexplorer.R

import kotlinx.android.synthetic.main.activity_hillforts_map.*

class HillfortsMapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillforts_map)
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

}
