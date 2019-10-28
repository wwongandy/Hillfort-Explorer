package org.wit.hillfortexplorer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(
    var id: Long = 0,
    var title: String = "",
    var description: String = ""
): Parcelable