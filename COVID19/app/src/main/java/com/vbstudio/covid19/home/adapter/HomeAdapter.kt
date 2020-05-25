package com.vbstudio.covid19.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.ui.adapter.BaseViewHolder
import com.vbstudio.covid19.core.utils.StringUtils
import com.vbstudio.covid19.home.dao.HomeFeedData
import com.vbstudio.covid19.home.ui.FragmentHome
import kotlinx.android.synthetic.main.item_country_aon.view.*
import kotlinx.android.synthetic.main.item_country_aon.view.tv_active
import kotlinx.android.synthetic.main.item_country_aon.view.tv_confirmed
import kotlinx.android.synthetic.main.item_country_aon.view.tv_deceased
import kotlinx.android.synthetic.main.item_country_aon.view.tv_recovered
import kotlinx.android.synthetic.main.item_state_aon.view.*

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
            itemView.tv_confirmed.text = StringUtils.formatNumberString(
                data?.confirmed ?: "---",
                true
            )
            itemView.tv_active.text = StringUtils.formatNumberString(
                data?.active ?: "---",
                true
            )
            itemView.tv_recovered.text = StringUtils.formatNumberString(
                data?.recovered ?: "---",
                true
            )
            itemView.tv_deceased.text = StringUtils.formatNumberString(
                data?.deaths ?: "---",
                true
            )
            itemView.tv_data_timestamp.text = StringUtils.formatDate(
                data?.lastupdatedtime ?: "---",
                "dd/MM/yyyy hh:mm:ss"
            )
        }
    }


    class StateViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<HomeFeedData>(
        parent,
        R.layout.item_state_aon
    ) {
        override fun onBind(data: HomeFeedData?) {
            itemView.tv_state_name.text = """- ${data?.state}"""
            itemView.tv_confirmed.text = StringUtils.formatNumberString(
                data?.confirmed ?: "---",
                false
            )
            itemView.tv_active.text = StringUtils.formatNumberString(
                data?.active ?: "---",
                false
            )
            itemView.tv_recovered.text = StringUtils.formatNumberString(
                data?.recovered ?: "---",
                false
            )
            itemView.tv_deceased.text = StringUtils.formatNumberString(
                data?.deaths ?: "---",
                false
            )
        }
    }
}