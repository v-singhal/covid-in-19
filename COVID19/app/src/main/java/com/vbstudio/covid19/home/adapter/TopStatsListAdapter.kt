package com.vbstudio.covid19.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.ui.adapter.BaseViewHolder
import com.vbstudio.covid19.home.dao.RegionItemData
import com.vbstudio.covid19.home.ui.*
import kotlinx.android.synthetic.main.item_top_stats_list_data.view.*


class TopStatsListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var resourceList: List<RegionItemData> = arrayListOf()
    private var statsType: StatsSectionView.StatsType = StatsSectionView.StatsType.Confirmed

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TopStatsViewHolder(parent, statsType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TopStatsViewHolder).onBind(resourceList[position], position)
    }

    override fun getItemCount(): Int {
        return resourceList.size ?: 0
    }

    fun refreshList(topStatsList: List<RegionItemData>) {
        this.resourceList = topStatsList;
        notifyDataSetChanged()
    }

    fun setupStyleRes(statsType: StatsSectionView.StatsType) {
        this.statsType = statsType
    }


    class TopStatsViewHolder(
        parent: ViewGroup,
        val statsType: StatsSectionView.StatsType
    ) : BaseViewHolder<RegionItemData>(
        parent,
        R.layout.item_top_stats_list_data
    ) {
        var statsItemView: StatsSectionView? = null

        init {
            statsItemView = when (statsType) {
                StatsSectionView.StatsType.Confirmed -> ConfirmedStatsView(itemView.context)
                StatsSectionView.StatsType.Active -> ActiveStatsView(itemView.context)
                StatsSectionView.StatsType.Recovered -> RecoveredStatsView(itemView.context)
                StatsSectionView.StatsType.Deceased -> DeceasedStatsView(itemView.context)
            }
            itemView.container_stats_view.addView(statsItemView)
        }

        override fun onCreate() {
        }

        override fun onBind(data: RegionItemData?, position: Int) {
            itemView.tv_state_name.text = data?.state
            statsItemView?.setCounter(
                when (statsType) {
                    StatsSectionView.StatsType.Confirmed -> data?.confirmedForUI
                    StatsSectionView.StatsType.Active -> data?.activeForUI
                    StatsSectionView.StatsType.Recovered -> data?.recoveredForUI
                    StatsSectionView.StatsType.Deceased -> data?.deathsForUI
                }
            )
        }
    }
}