package org.wit.hillfortexplorer.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.helpers.checkLocationPermissions
import org.wit.hillfortexplorer.helpers.isPermissionGranted
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.Location
import org.wit.hillfortexplorer.views.*
import java.util.*
import kotlin.collections.ArrayList

class HillfortPresenter(view: BaseView): BasePresenter(view) {

    var hillfort = HillfortModel()
    var edit = false

    var map: GoogleMap? = null
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)

    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    fun doCreateOrUpdate(title: String, description: String, additionalNotes: String, isVisited: Boolean, rating: Int, favourite: Boolean) {
        hillfort.title = title
        hillfort.description = description
        hillfort.additionalNotes = additionalNotes
        hillfort.isVisited = isVisited
        hillfort.rating = rating
        hillfort.favourite = favourite

        doAsync {
            if (edit) {
                app.hillforts.update(hillfort.copy())
            } else {
                app.hillforts.create(hillfort.copy())
            }

            uiThread {
                view?.setResult(AppCompatActivity.RESULT_OK)
                view?.finish()
            }
        }
    }

    fun doRemoveImage(currentImageItem: Int) {
        val newImageList = ArrayList(hillfort.images)

        newImageList.removeAt(currentImageItem)
        hillfort.images = newImageList
    }

    fun doSetLocation() {
        view?.navigateTo(VIEW.EDITLOCATION, LOCATION_REQUEST, "location", Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom))
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
            R.id.item_share -> {
                if (edit) {
                    var intent = Intent()

                    // Allow sharing the text to other apps
                    intent.setAction(Intent.ACTION_SEND)
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        "I am using the HillfortExplorer app! I found ${hillfort.title}!"
                    )
                    intent.setType("text/plain")

                    // Perform the sharing
                    view?.startActivity(intent)
                } else {
                    view?.toast("You need to save the Hillfort first before sharing!")
                }
            }

            R.id.item_delete -> {
                doAsync {
                    app.hillforts.delete(hillfort)

                    uiThread {
                        view?.finish()
                    }
                }
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
                    locationUpdate(location.lat, location.lng)
                }
            }
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(hillfort.location.lat, hillfort.location.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        hillfort.location.lat = lat
        hillfort.location.lng = lng
        hillfort.location.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.location.lat, hillfort.location.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.location.lat, hillfort.location.lng), hillfort.location.zoom))
        view?.updateHillfortMapView(hillfort)
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            // permissions denied, so use the default location
            locationUpdate(defaultLocation.lat, defaultLocation.lng)
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }
}