package org.wit.hillfortexplorer.activities

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.intentFor
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.Location
import java.util.*
import kotlin.collections.ArrayList

class HillfortPresenter(val view: HillfortActivity) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    val MAX_HILLFORT_IMAGES_ALLOWED = 4

    var app: MainApp
    var hillfort = HillfortModel()
    var edit = false

    init {
        app = view.application as MainApp

        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showToEditHillfort(hillfort)
        }
    }

    fun doCreateOrUpdate() {
        hillfort.title = view.hillfortTitle.text.toString()
        hillfort.description = view.description.text.toString()
        hillfort.additionalNotes = view.additionalNotes.text.toString()
        hillfort.isVisited = view.isVisited.isChecked

        if (edit) {
            app.hillforts.update(hillfort.copy(), app.currentUser.id)
        } else {
            app.hillforts.create(hillfort.copy(), app.currentUser.id)
        }

        view.setResult(AppCompatActivity.RESULT_OK)
        view.finish()
    }

    fun doRemoveImage() {
        val currentImageItem = view.formImagePager.currentItem
        val newImageList = ArrayList(hillfort.images)

        newImageList.removeAt(currentImageItem)
        hillfort.images = newImageList

        view.updateHillfortImagesView(hillfort)
    }

    fun doShowLocationSelectionMap() {
        val location = Location(52.245696, -7.139102, 15f)
        if (hillfort.location.zoom != 0f) {
            location.lat = hillfort.location.lat
            location.lng = hillfort.location.lng
            location.zoom = hillfort.location.zoom
        }

        view.startActivityForResult(view.intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    fun doUpdateVisitedFlag() {
        hillfort.isVisited = view.isVisited.isChecked
        view.updateVisitedCheckbox(hillfort)
    }

    fun doSetVisitedDate(choseYear: Int, choseMonth: Int, choseDay: Int) {
        val parsedDate = Date(choseYear, choseMonth, choseDay)
        hillfort.dateVisited = parsedDate
        view.notifyVisitedDate(hillfort)
    }

    fun doOptionsItemSelected(item: MenuItem) {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.hillforts.delete(hillfort)
                view.finish()
            }
        }
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    if (hillfort.images.size >= MAX_HILLFORT_IMAGES_ALLOWED) {
                        view.notifyExceededImagesLimit()
                    } else {
                        val newImageList = ArrayList(hillfort.images)
                        val newImage = data.getData().toString()

                        newImageList.add(newImage)
                        hillfort.images = newImageList
                        view.updateHillfortImagesView(hillfort)

                        if (hillfort.images.size == 2) {
                            view.notifySwipeForImagesViewing()
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
}