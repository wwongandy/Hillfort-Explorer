package org.wit.hillfortexplorer.views.hillfort

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.View
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

    val MAX_HILLFORT_IMAGES_ALLOWED = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)

        init(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = initPresenter(HillfortPresenter(this)) as HillfortPresenter

        if (presenter.edit) {
            btnAdd.setText(R.string.save_hillfort)
        }

        if (presenter.hillfort.images.isEmpty()) {
            imageLayout.visibility = View.GONE
            removeImage.visibility = View.GONE
        }

        if (!presenter.hillfort.isVisited) {
            dateVisited.visibility = View.GONE
        }

        btnAdd.setOnClickListener() {
            if (hillfortTitle.text.toString().isEmpty() || description.text.toString().isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                presenter.doCreateOrUpdate(hillfortTitle.text.toString(), description.text.toString(), additionalNotes.text.toString(), isVisited.isChecked)
            }
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

        hillfortLocation.setOnClickListener {
            presenter.doShowLocationSelectionMap()
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
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
}
