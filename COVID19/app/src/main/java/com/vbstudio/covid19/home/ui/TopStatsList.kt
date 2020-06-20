package com.vbstudio.covid19.home.ui

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.vbstudio.covid19.R
import com.vbstudio.covid19.home.adapter.TopStatsListAdapter
import com.vbstudio.covid19.home.dao.RegionItemData
import kotlinx.android.synthetic.main.top_stats_list_view.view.*

class TopStatsList : FrameLayout {

    private val topStatsListAdapter = TopStatsListAdapter()

    constructor(context: Context)
            : super(context) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    init {
        this.addView(
            LayoutInflater.from(context).inflate(R.layout.top_stats_list_view, null, false)
        )
        rv_top_stats_list.adapter = topStatsListAdapter
        rv_top_stats_list.layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    }

    fun setupList(
        headerString: String,
        statsList: List<RegionItemData>,
        statsType: StatsSectionView.StatsType
    ) {
        tv_header.text = headerString
        topStatsListAdapter.setupStyleRes(statsType)
        topStatsListAdapter.refreshList(statsList)
    }
}