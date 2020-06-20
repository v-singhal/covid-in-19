package com.vbstudio.covid19.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.ui.adapter.BaseViewHolder
import com.vbstudio.covid19.home.dao.RegionItemData
import kotlinx.android.synthetic.main.item_state_aon.view.*

class StateListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var regionItemDataList: List<RegionItemData>? = null

    companion object {
        const val OPACITY_EVEN_ROW = 0.2F
        const val OPACITY_ODD_ROW = 0F

        enum class FeedRowType {
            COUNTRY,
            STATE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return regionItemDataList?.get(position)?.type!!.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StateViewHolder).onBind(regionItemDataList?.get(position), position)
    }

    override fun getItemCount(): Int {
        return regionItemDataList?.size ?: 0
    }

    fun refreshList(regionItemDataList: List<RegionItemData>?) {
        this.regionItemDataList = regionItemDataList;
        notifyDataSetChanged()
    }


    class StateViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<RegionItemData>(
        parent,
        R.layout.item_state_aon
    ) {

        override fun onCreate() {
        }

        override fun onBind(data: RegionItemData?, position: Int) {
            itemView.tv_state_name.text = data?.state
            itemView.container_confirmed.setCounter(data?.confirmedForUI)
            itemView.container_active.setCounter(data?.activeForUI)
            itemView.container_recovered.setCounter(data?.recoveredForUI)
            itemView.container_deceased.setCounter(data?.deathsForUI)
        }
    }
}