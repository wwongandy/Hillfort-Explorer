package org.wit.hillfortexplorer.views.hillfort

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.Location
import org.wit.hillfortexplorer.views.*
import java.util.*
import kotlin.collections.ArrayList

class HillfortPresenter(view: BaseView): BasePresenter(view) {

    var hillfort = HillfortModel()
    var edit = false
    var map: GoogleMap ?= null

    var defaultLocation = Location(52.245696, -7.139102, 15f)

    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        } else {
            hillfort.location = defaultLocation
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
        if (edit) {
            view?.navigateTo(VIEW.EDITLOCATION, LOCATION_REQUEST, "location", hillfort.location)
        } else {
            view?.navigateTo(VIEW.EDITLOCATION, LOCATION_REQUEST, "location", defaultLocation)
        }
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
                    locationUpdate(location)
                }
            }
        }
    }

    fun doConfigureMap(_map: GoogleMap) {
        map = _map
        locationUpdate(hillfort.location)
    }

    fun locationUpdate(location: Location) {
        val latitude = location.lat
        val longitude = location.lng

        hillfort.location.lat = latitude
        hillfort.location.lng = longitude
        hillfort.location.zoom = 15f

        map?.clear()
        map?.uiSettings?.isZoomControlsEnabled = true

        val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.location.lat, hillfort.location.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.location.lat, hillfort.location.lng), 15f))
        view?.showHillfort(hillfort)
    }
}