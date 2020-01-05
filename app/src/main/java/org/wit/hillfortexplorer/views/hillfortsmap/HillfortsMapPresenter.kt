package org.wit.hillfortexplorer.views.hillfortsmap

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillfortexplorer.helpers.checkLocationPermissions
import org.wit.hillfortexplorer.helpers.createDefaultLocationRequest
import org.wit.hillfortexplorer.helpers.isPermissionGranted
import org.wit.hillfortexplorer.main.MainApp
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.Location
import org.wit.hillfortexplorer.views.BasePresenter
import org.wit.hillfortexplorer.views.BaseView

class HillfortsMapPresenter(view: BaseView): BasePresenter(view) {

    var map: GoogleMap ?= null

    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()

    var userLocation = defaultLocation.copy()
    var selectedHillfort: HillfortModel? = null

    init {
        if (checkLocationPermissions(view)) {
            doSetCurrentLocation()
        }
    }

    fun doConfigureMap(_map: GoogleMap) {
        map = _map
        map?.uiSettings?.isZoomControlsEnabled = true

        doAsync {
            val hillforts = app.hillforts.findAll()

            uiThread {
                hillforts.forEach {
                    val loc = LatLng(it.location.lat, it.location.lng)
                    val options = MarkerOptions().title(it.title).position(loc)
                    map?.addMarker(options)?.tag = it

                    // Move camera to most recent Hillfort
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
                }
            }
        }
    }

    /**
     * Continually track the user's current location so it can be used for the directions functionality
     */

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            // permissions denied, so use the default location
            userLocation = defaultLocation.copy()
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            userLocation = Location(it.latitude, it.longitude)
        }
    }

    @SuppressLint("MissingPermission")
    fun doRestartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    userLocation = Location(l.latitude, l.longitude)
                }
            }
        }

        locationService.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun notifySelectedHillfort(hillfort: HillfortModel) {
        selectedHillfort = hillfort
    }

    fun doHandleDirections() {
        if (selectedHillfort != null) {

            // Source: userLocation
            // Destination: selectedHillfort.location

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/dir/?api=1&origin=${userLocation.lat},${userLocation.lng}&destination=${selectedHillfort?.location?.lat},${selectedHillfort?.location?.lng}")
            )

            view?.startActivity(intent)
        }
    }
}