package org.wit.hillfortexplorer.views.hillfort

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.helpers.showImagePicker
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.ImagePagerAdapter
import org.wit.hillfortexplorer.views.BaseView
import org.wit.hillfortexplorer.views.IMAGE_REQUEST
import java.util.*

class HillfortView : BaseView(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    lateinit var map: GoogleMap

    val MAX_HILLFORT_IMAGES_ALLOWED = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        init(toolbarAdd, true)

        presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

        if (presenter.hillfort.images.isEmpty()) {
            imageLayout.visibility = View.GONE
            removeImage.visibility = View.GONE
        }

        if (!presenter.hillfort.isVisited) {
            dateVisited.visibility = View.GONE
        }

        chooseImage.setOnClickListener {
            if (presenter.hillfort.images.size >= MAX_HILLFORT_IMAGES_ALLOWED) {
                toast("You cannot have more than $MAX_HILLFORT_IMAGES_ALLOWED images")
            } else {
                showImagePicker(this, IMAGE_REQUEST)
            }
        }

        removeImage.setOnClickListener {
            presenter.doRemoveImage(formImagePager.currentItem)
            updateHillfortImagesView(presenter.hillfort)
        }

        isVisited.setOnClickListener {
            presenter.doUpdateVisitedFlag(isVisited.isChecked)

            if (presenter.hillfort.isVisited) {
                dateVisited.visibility = View.VISIBLE
            } else {
                dateVisited.visibility = View.GONE
            }
        }

        // Setting up the date picker dialog
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        dateVisited.setOnClickListener {
            // Show date picker dialog
            val datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener {datePicker, choseYear, choseMonth, choseDay ->
                    // Let presenter handle Hillfort manipulation
                    presenter.doSetVisitedDate(choseYear, choseMonth, choseDay)
                },
                year,
                month,
                day
            )

            datePicker.show()

            if (presenter.hillfort.dateVisited != null) {
                toast("Date Visited: ${presenter.hillfort.dateVisited.toString()}")
            }
        }

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.item_save -> {
                if (hillfortTitle.text.toString().isEmpty() || description.text.toString().isEmpty()) {
                    toast(R.string.enter_hillfort_title)
                } else {
                    presenter.doCreateOrUpdate(hillfortTitle.text.toString(), description.text.toString(), additionalNotes.text.toString(), isVisited.isChecked)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        additionalNotes.setText(hillfort.additionalNotes)
        isVisited.isChecked = hillfort.isVisited
        updateHillfortImagesView(hillfort)
    }

    override fun updateHillfortImagesView(hillfort: HillfortModel) {
        if (hillfort.images.isEmpty()) {
            imageLayout.visibility = View.GONE
            removeImage.visibility = View.GONE
        } else {
            imageLayout.visibility = View.VISIBLE
            removeImage.visibility = View.VISIBLE
            chooseImage.setText(R.string.select_more_images)

            formImagePager.adapter = ImagePagerAdapter(this, hillfort.images, R.layout.form_image_hillfort, R.id.formImageIcon)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun updateHillfortMapView(hillfort: HillfortModel) {
        hillfortLocation.setText("%.6f".format(hillfort.location.lat) + ", %.6f".format(hillfort.location.lng))
    }
}
