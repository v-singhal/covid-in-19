package com.vbstudio.covid19.home.dao

data class StateListData(
    val regionItemDataList: List<RegionItemData>,
    override val dataType: Int
) : HomeBaseData(dataType)