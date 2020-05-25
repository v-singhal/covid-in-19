package com.vbstudio.covid19.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.ui.adapter.BaseViewHolder
import com.vbstudio.covid19.home.dao.HomeFeedData
import com.vbstudio.covid19.home.ui.FragmentHome
import kotlinx.android.synthetic.main.item_country_aon.view.*

class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var homeFeedDataList: List<HomeFeedData>? = null

    override fun getItemViewType(position: Int): Int {
        return homeFeedDataList?.get(position)?.type!!.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FragmentHome.FeedRowType.COUNTRY.ordinal -> CountryViewHolder(parent)
            FragmentHome.FeedRowType.STATE.ordinal -> StateViewHolder(parent)
            else -> StateViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CountryViewHolder) {
            holder.onBind(homeFeedDataList?.get(position))
        } else if (holder is StateViewHolder) {
            holder.onBind(homeFeedDataList?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return homeFeedDataList?.size ?: 0
    }

    fun refreshList(homeFeedDataList: List<HomeFeedData>?) {
        this.homeFeedDataList = homeFeedDataList;
        notifyDataSetChanged()
    }

    class CountryViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<HomeFeedData>(
        parent,
        R.layout.item_country_aon
    ) {
        override fun onBind(data: HomeFeedData?) {
            itemView.tv_confirmed.text = data?.confirmed ?: "---"
            itemView.tv_active.text = data?.active ?: "---"
            itemView.tv_recovered.text = data?.recovered ?: "---"
            itemView.tv_deceased.text = data?.deaths ?: "---"
            itemView.tv_data_timestamp.text = data?.lastupdatedtime ?: "---"
        }
    }


    class StateViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<HomeFeedData>(
        parent,
        R.layout.item_state_aon
    ) {
        override fun onBind(data: HomeFeedData?) {

        }
    }
}