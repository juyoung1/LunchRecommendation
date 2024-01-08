package com.example.lunchrecommendation.data.dao

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MenuImageDao (

    var menuImage: String? = null,
): Parcelable