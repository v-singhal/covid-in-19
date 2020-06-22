package com.vbstudio.covid19.home.dao

import android.os.Parcelable
import com.vbstudio.covid19.core.utils.NumberUtils
import com.vbstudio.covid19.core.utils.StringUtils
import com.vbstudio.covid19.home.adapter.StateListAdapter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegionItemData(

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

    val recoveredForUI: String? = null,

    val activeForUI: String? = null,

    val confirmedForUI: String? = null,

    val deathsForUI: String? = null,

    val activePercent: Float? = null,

    val recoveryPercent: Float? = null,

    val deceasedPercent: Float? = null,

    val lastupdatedtimeForUI: String? = null,

    val type: StateListAdapter.Companion.FeedRowType = StateListAdapter.Companion.FeedRowType.STATE
) : Parcelable {

    // State Code for total cases in country
    companion object {
        val STATE_CODE_TOTAL = "TT"

        private fun formatNumbers(inputString: String?, statecode: String?): String {
            return StringUtils.formatNumberString(
                inputString ?: "---",
                getItemType(statecode) == StateListAdapter.Companion.FeedRowType.COUNTRY
            )
        }

        private fun getItemType(statecode: String?): StateListAdapter.Companion.FeedRowType {
            return if (statecode == STATE_CODE_TOTAL)
                StateListAdapter.Companion.FeedRowType.COUNTRY
            else
                StateListAdapter.Companion.FeedRowType.STATE
        }

        private fun formatTimestamp(inputTimestamp: String?): String {
            return StringUtils.formatDate(
                inputTimestamp,
                "dd/MM/yyyy hh:mm:ss"
            )
        }
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
        formatNumbers(stateLatestData.recovered, stateLatestData.statecode),
        formatNumbers(stateLatestData.active, stateLatestData.statecode),
        formatNumbers(stateLatestData.confirmed, stateLatestData.statecode),
        formatNumbers(stateLatestData.deaths, stateLatestData.statecode),
        NumberUtils.getPercentage(stateLatestData.active, stateLatestData.confirmed),
        NumberUtils.getPercentage(stateLatestData.recovered, stateLatestData.confirmed),
        NumberUtils.getPercentage(stateLatestData.deaths, stateLatestData.confirmed),
        formatTimestamp(stateLatestData.lastupdatedtime),
        type = getItemType(stateLatestData.statecode)

    )
}
