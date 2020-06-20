package com.vbstudio.covid19.home.utils

import com.vbstudio.covid19.home.adapter.StateListAdapter
import com.vbstudio.covid19.home.dao.RegionItemData

class StateDataUtils {

    companion object {

        fun getStatesFromList(regionItemDataList: List<RegionItemData>): List<RegionItemData> {
            return regionItemDataList.filter { regionItemData ->
                regionItemData.type == StateListAdapter.Companion.FeedRowType.STATE
            }
        }
    }
}