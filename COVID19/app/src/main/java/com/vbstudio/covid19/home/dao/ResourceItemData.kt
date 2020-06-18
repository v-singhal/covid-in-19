package com.vbstudio.covid19.home.dao

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResourceItemData(

    @field:SerializedName("recordid")
    val recordid: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("descriptionandorserviceprovided")
    val descriptionandorserviceprovided: String? = null,

    @field:SerializedName("contact")
    val contact: String? = null,

    @field:SerializedName("phonenumber")
    val phonenumber: String? = null,

    @field:SerializedName("state")
    val state: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("nameoftheorganisation")
    val nameoftheorganisation: String? = null
) : Parcelable
