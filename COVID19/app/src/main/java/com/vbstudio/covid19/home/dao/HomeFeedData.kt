package com.vbstudio.covid19.home.dao

import android.os.Parcelable
import com.vbstudio.covid19.home.ui.FragmentHome
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeFeedData(

    val recovered: String? = null,

    val deltadeaths: String? = null,

    val deltarecovered: String? = null,

    val active: String? = null,

    val deltaconfirmed: String? = null,

    val state: String? = null,

    val statecode: String? = null,

    val confirmed: String? = null,

    val deaths: String? = null,

    val lastupdatedtime: String? = null,

    val type: FragmentHome.FeedRowType = FragmentHome.FeedRowType.STATE
) : Parcelable {

    // State Code for total cases in country
    companion object {
        val STATE_CODE_TOTAL = "TT"
    }

    constructor(stateLatestData: StateLatestData) : this(
        stateLatestData.recovered,
        stateLatestData.deltadeaths,
        stateLatestData.deltarecovered,
        stateLatestData.active,
        stateLatestData.deltaconfirmed,
        stateLatestData.state,
        stateLatestData.statecode,
        stateLatestData.confirmed,
        stateLatestData.deaths,
        stateLatestData.lastupdatedtime,
        type = if (stateLatestData.statecode == STATE_CODE_TOTAL)
            FragmentHome.FeedRowType.COUNTRY
        else
            FragmentHome.FeedRowType.STATE

    )
}