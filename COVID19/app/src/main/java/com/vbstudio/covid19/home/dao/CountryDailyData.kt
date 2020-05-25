package com.vbstudio.covid19.home.dao

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryDailyData(
    @SerializedName("dailyconfirmed")
    val dailyconfirmed: Int,

    @SerializedName("dailydeceased")
    val dailydeceased: Int,

    @SerializedName("dailyrecovered")
    val dailyrecovered: Int,

    @SerializedName("date")
    val date: String,

    @SerializedName("totalconfirmed")
    val totalconfirmed: Int,

    @SerializedName("totaldeceased")
    val totaldeceased: Int,

    @SerializedName("totalrecovered")
    val totalrecovered: Int
) : Parcelable