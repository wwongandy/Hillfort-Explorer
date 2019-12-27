package org.wit.hillfortexplorer.views.hillfort

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.Location
import org.wit.hillfortexplorer.views.*
import java.util.*
import kotlin.collections.ArrayList

class HillfortPresenter(view: BaseView): BasePresenter(view) {

    var hillfort = HillfortModel()
    var edit = false

    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        }
    }

    fun doCreateOrUpdate(title: String, description: String, additionalNotes: String, isVisited: Boolean) {
        hillfort.title = title
        hillfort.description = description
        hillfort.additionalNotes = additionalNotes
        hillfort.isVisited = isVisited

        if (edit) {
            app.hillforts.update(hillfort.copy(), app.currentUser.id)
        } else {
            app.hillforts.create(hillfort.copy(), app.currentUser.id)
        }

        view?.setResult(AppCompatActivity.RESULT_OK)
        view?.finish()
    }

    fun doRemoveImage(currentImageItem: Int) {
        val newImageList = ArrayList(hillfort.images)

        newImageList.removeAt(currentImageItem)
        hillfort.images = newImageList
    }

    fun doShowLocationSelectionMap() {
        val location = Location(52.245696, -7.139102, 15f)
        if (hillfort.location.zoom != 0f) {
            location.lat = hillfort.location.lat
            location.lng = hillfort.location.lng
            location.zoom = hillfort.location.zoom
        }

        view?.navigateTo(VIEW.EDITLOCATION, LOCATION_REQUEST, "location", location)
    }

    fun doUpdateVisitedFlag(isVisited: Boolean) {
        hillfort.isVisited = isVisited
    }

    fun doSetVisitedDate(choseYear: Int, choseMonth: Int, choseDay: Int) {
        val parsedDate = Date(choseYear, choseMonth, choseDay)
        hillfort.dateVisited = parsedDate
    }

    override fun doOptionsItemSelected(item: MenuItem) {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.hillforts.delete(hillfort)
                view?.finish()
            }
        }
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    val newImageList = ArrayList(hillfort.images)
                    val newImage = data.getData().toString()

                    newImageList.add(newImage)
                    hillfort.images = newImageList
                    view?.updateHillfortImagesView(hillfort)
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