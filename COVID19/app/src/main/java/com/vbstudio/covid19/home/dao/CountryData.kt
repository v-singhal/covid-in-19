package com.vbstudio.covid19.home.dao

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryData(
    @SerializedName("cases_time_series")
    val countryDailyData: List<CountryDailyData>? = null,

    @SerializedName("tested")
    val countryDayTestingTimelineData: List<CountryTestingDailyData>? = null,

    @SerializedName("statewise")
    val stateLatestData: List<StateLatestData>? = null
) : Parcelable