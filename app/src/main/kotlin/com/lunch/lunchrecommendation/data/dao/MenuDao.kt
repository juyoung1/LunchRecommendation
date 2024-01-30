package com.lunch.lunchrecommendation.data.dao

import com.google.gson.annotations.SerializedName

data class MenuDao (

    @JvmField
    @SerializedName("menu")
    var menu: String? = null,

    @JvmField
    @SerializedName("menuImage")
    var menuImage: String? = null,

    var isSelected: Boolean = false
)