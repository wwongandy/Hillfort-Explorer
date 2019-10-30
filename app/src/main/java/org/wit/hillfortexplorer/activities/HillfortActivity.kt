package org.wit.hillfortexplorer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.helpers.readImage
import org.wit.hillfortexplorer.helpers.readImageFromPath
import org.wit.hillfortexplorer.helpers.showImagePicker
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.Location

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app : MainApp

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        app = application as MainApp
        info("Hillfort Activity started..")

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        var edit = false
        if (intent.hasExtra("hillfort_edit")) {
            edit = true

            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)

            if (hillfort.images.isNotEmpty()) {
                hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.images[0]))
                chooseImage.setText(R.string.select_more_images)
            }

            btnAdd.setText(R.string.save_hillfort)
        }

        btnAdd.setOnClickListener() {

            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()

            if (hillfort.title.isEmpty() || hillfort.description.isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {

                if (edit) {
                    app.hillforts.update(hillfort.copy())
                } else {
                    app.hillforts.create(hillfort.copy())
                }
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        hillfortLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (hillfort.location.zoom != 0f) {
                location.lat = hillfort.location.lat
                location.lng = hillfort.location.lng
                location.zoom = hillfort.location.zoom
            }

            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> { finish() }
            R.id.item_delete -> {
                app.hillforts.delete(hillfort)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    val newImageList = ArrayList(hillfort.images)
                    newImageList.add(data.getData().toString())

                    hillfort.images = newImageList
                    hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.select_more_images)

                    if (hillfort.images.size == 2) {
                        toast("Swipe to view your other images")
                    }
                }
            }

            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    hillfort.location = location
                }
            }
        }
    }
}
