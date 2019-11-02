package org.wit.hillfortexplorer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class HillfortModel(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var additionalNotes: String = "",
    var isVisited: Boolean = false,
    var dateVisited: Date? = null,
    var images: List<String> = ArrayList(),
    var location: Location = Location()
): Parcelable

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable