package com.vbstudio.covid19.home.dao

data class HomeData(
    val regionItemData: RegionItemData,
    override val dataType: Int
) : HomeBaseData(dataType)