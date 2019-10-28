package org.wit.hillfortexplorer.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.models.Location

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private lateinit var map: GoogleMap
    var location = Location()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        location = intent.extras?.getParcelable<Location>("location")!!
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMarkerDragListener(this)

        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Hillfort")
            .snippet("GPS: " + loc.toString())
            .draggable(true)
            .position(loc)

        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }

    override fun onMarkerDrag(marker: Marker) {

    }

    override fun onMarkerDragEnd(marker: Marker) {
        location.lat = marker.position.latitude
        location.lng = marker.position.longitude
        location.zoom = map.cameraPosition.zoom
    }

    override fun onMarkerDragStart(marker: Marker) {

    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra("location", location)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
        super.onBackPressed()
    }
}
