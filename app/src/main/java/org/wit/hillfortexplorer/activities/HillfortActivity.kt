package org.wit.hillfortexplorer.activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.helpers.showImagePicker
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.ImagePagerAdapter
import org.wit.hillfortexplorer.models.Location
import java.util.*
import kotlin.collections.ArrayList

val MAX_HILLFORT_IMAGES_ALLOWED = 4

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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var edit = false
        if (intent.hasExtra("hillfort_edit")) {
            edit = true

            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)
            additionalNotes.setText(hillfort.additionalNotes)
            isVisited.isChecked = hillfort.isVisited

            if (hillfort.images.isNotEmpty()) {
                updateImagePager()
                chooseImage.setText(R.string.select_more_images)

                if (hillfort.images.size > 1) {
                    toast("Swipe to view your other images")
                }
            }

            btnAdd.setText(R.string.save_hillfort)
        }

        if (hillfort.images.isEmpty()) {
            imageLayout.visibility = View.GONE
            removeImage.visibility = View.GONE
        }

        if (!hillfort.isVisited) {
            dateVisited.visibility = View.GONE
        }

        btnAdd.setOnClickListener() {

            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()
            hillfort.additionalNotes = additionalNotes.text.toString()
            hillfort.isVisited = isVisited.isChecked

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

        removeImage.setOnClickListener {
            val currentImageItem = formImagePager.currentItem
            val newImageList = ArrayList(hillfort.images)

            newImageList.removeAt(currentImageItem)
            hillfort.images = newImageList

            if (hillfort.images.isEmpty()) {
                imageLayout.visibility = View.GONE
                removeImage.visibility = View.GONE
            } else {
                updateImagePager()
            }

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

        isVisited.setOnClickListener {
            hillfort.isVisited = isVisited.isChecked

            if (hillfort.isVisited) {
                dateVisited.visibility = View.VISIBLE
            } else {
                dateVisited.visibility = View.GONE
            }
        }

        // Setting up date picker dialog
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        dateVisited.setOnClickListener {
            // Date picker dialog
            val datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener {datePicker, choseYear, choseMonth, choseDay ->
                    val parsedDate = Date(choseYear, choseMonth, choseDay)
                    hillfort.dateVisited = parsedDate
                },
                year,
                month,
                day
            )

            datePicker.show()

            if (hillfort.dateVisited != null) {
                toast("Date visited: ${hillfort.dateVisited}")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
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
                    if (hillfort.images.size >= MAX_HILLFORT_IMAGES_ALLOWED) {
                        toast("You cannot have more than $MAX_HILLFORT_IMAGES_ALLOWED images")
                    } else {
                        val newImageList = ArrayList(hillfort.images)
                        val newImage = data.getData().toString()

                        newImageList.add(newImage)
                        hillfort.images = newImageList
                        imageLayout.visibility = View.VISIBLE
                        updateImagePager()

                        chooseImage.setText(R.string.select_more_images)
                        removeImage.visibility = View.VISIBLE

                        if (hillfort.images.size == 2) {
                            toast("Swipe to view your other images")
                        }
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

    private fun updateImagePager() {
        formImagePager.adapter = ImagePagerAdapter(this, hillfort.images, R.layout.form_image_hillfort, R.id.formImageIcon)
    }
}
