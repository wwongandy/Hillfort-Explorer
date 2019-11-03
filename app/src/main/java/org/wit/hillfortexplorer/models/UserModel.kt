package org.wit.hillfortexplorer.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel (
    var id: Long = 0,
    var username: String = "",
    var password: String = ""
): Parcelable