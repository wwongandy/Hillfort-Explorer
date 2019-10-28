package org.wit.hillfortexplorer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hillfortexplorer.R
import org.wit.hillfortexplorer.models.Location

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
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
        mMap = googleMap

        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Hillfort")
            .snippet("GPS: " + loc.toString())
            .draggable(true)
            .position(loc)

        mMap.addMarker(options)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    }
}
