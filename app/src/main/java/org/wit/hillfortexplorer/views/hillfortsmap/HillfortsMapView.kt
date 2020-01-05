package org.wit.hillfortexplorer.views.hillfortsmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.hillfortexplorer.R

import kotlinx.android.synthetic.main.activity_hillforts_map.*
import kotlinx.android.synthetic.main.content_hillforts_map.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.hillfortexplorer.models.HillfortModel
import org.wit.hillfortexplorer.models.ImagePagerAdapter
import org.wit.hillfortexplorer.views.BaseView

class HillfortsMapView : BaseView(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: HillfortsMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillforts_map)
        presenter = (initPresenter(HillfortsMapPresenter(this))) as HillfortsMapPresenter

        init(toolbar, true)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMarkerClickListener(this)
        }

        directions.setOnClickListener {
            presenter.doHandleDirections()
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val hillfort = marker.tag as HillfortModel

        currentTitle.text = hillfort!!.title
        currentDescription.text = hillfort!!.description
        mapsImagePager.adapter = ImagePagerAdapter(this, hillfort.images, R.layout.form_image_hillfort, R.id.formImageIcon)

        presenter.notifySelectedHillfort(hillfort)

        return true
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
        presenter.doRestartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}
