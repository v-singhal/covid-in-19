package com.vbstudio.covid19.home.dao

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResourceListData(

    @SerializedName("resources")
    val resourceList: List<ResourceItemData>? = null
) : Parcelable
