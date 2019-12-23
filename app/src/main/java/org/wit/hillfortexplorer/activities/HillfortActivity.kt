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
import org.jetbrains.anko.toast
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.helpers.showImagePicker
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.ImagePagerAdapter
import java.util.*

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    lateinit var presenter: HillfortPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        presenter = HillfortPresenter(this)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (presenter.hillfort.images.isEmpty()) {
            imageLayout.visibility = View.GONE
            removeImage.visibility = View.GONE
        }

        if (!presenter.hillfort.isVisited) {
            dateVisited.visibility = View.GONE
        }

        btnAdd.setOnClickListener() {
            if (hillfortTitle.toString().isEmpty() || description.toString().isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                presenter.doCreateOrUpdate()
            }
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, presenter.IMAGE_REQUEST)
        }

        removeImage.setOnClickListener {
            presenter.doRemoveImage()
        }

        hillfortLocation.setOnClickListener {
            presenter.doShowLocationSelectionMap()
        }

        isVisited.setOnClickListener {
            presenter.doUpdateVisitedFlag()
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
            notifyVisitedDate(presenter.hillfort)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        presenter.doOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.doActivityResult(requestCode, resultCode, data)
    }

    fun showToEditHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        additionalNotes.setText(hillfort.additionalNotes)
        isVisited.isChecked = hillfort.isVisited
        btnAdd.setText(R.string.save_hillfort)

        updateHillfortImagesView(hillfort)
        notifyVisitedDate(hillfort)

        if (hillfort.images.size > 1) {
            notifySwipeForImagesViewing()
        }
    }

    fun updateHillfortImagesView(hillfort: HillfortModel) {
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

    fun updateVisitedCheckbox(hillfort: HillfortModel) {
        if (hillfort.isVisited) {
            dateVisited.visibility = View.VISIBLE
        } else {
            dateVisited.visibility = View.GONE
        }
    }

    fun notifyVisitedDate(hillfort: HillfortModel) {
        if (hillfort.dateVisited != null) {
            toast("Date visited: ${hillfort.dateVisited}")
        }
    }

    fun notifyExceededImagesLimit() {
        toast("You cannot have more than $presenter.MAX_HILLFORT_IMAGES_ALLOWED images")
    }

    fun notifySwipeForImagesViewing() {
        toast("Swipe to view your other images")
    }
}
