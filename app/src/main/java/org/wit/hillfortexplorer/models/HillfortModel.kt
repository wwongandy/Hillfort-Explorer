package org.wit.hillfortexplorer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(
    var title: String = "",
    var description: String = ""
): Parcelable