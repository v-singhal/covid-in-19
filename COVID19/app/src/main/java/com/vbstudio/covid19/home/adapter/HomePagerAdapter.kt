package com.vbstudio.covid19.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.ui.adapter.BaseViewHolder
import com.vbstudio.covid19.home.dao.HomeBaseData
import com.vbstudio.covid19.home.dao.HomeData
import com.vbstudio.covid19.home.dao.RegionItemData
import com.vbstudio.covid19.home.dao.StateListData
import kotlinx.android.synthetic.main.fragment_states.view.*
import kotlinx.android.synthetic.main.item_country_aon.view.*

class HomePagerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tabDataList: List<HomeBaseData>? = null

    private lateinit var animationItemRevealCallback: (view: View?) -> Unit

    companion object {
        enum class PageType {
            HOME,
            STATE_LIST,
            RESOURCE_LIST
        }
    }

    override fun getItemViewType(position: Int): Int {
        return tabDataList?.get(position)?.dataType ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PageType.HOME.ordinal -> HomeViewHolder(parent)
            PageType.STATE_LIST.ordinal -> StateListViewHolder(parent)
            PageType.RESOURCE_LIST.ordinal -> ResourcesViewHolder(parent)
            else -> StateListViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HomeViewHolder) {
            holder.onBind(tabDataList?.get(position) as HomeData, position)
        } else if (holder is StateListViewHolder) {
            holder.onBind(tabDataList?.get(position) as StateListData, position)
        } else if (holder is ResourcesViewHolder) {
            holder.onBind(tabDataList?.get(position), position)
        }

        animateItemReveal(holder.itemView);
    }

    private fun animateItemReveal(view: View?) {
        animationItemRevealCallback?.let {
            animationItemRevealCallback.invoke(view)
        }
    }

    override fun getItemCount(): Int {
        return tabDataList?.size ?: 0
    }

    fun refreshList(tabDataList: List<HomeBaseData>?) {
        this.tabDataList = tabDataList;
        notifyDataSetChanged()
    }

    fun addItemRevealCallback(animationItemRevealCallback: ((view: View?) -> Unit)) {
        this.animationItemRevealCallback = animationItemRevealCallback
    }

    class HomeViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<HomeData>(
        parent,
        R.layout.fragment_home
    ) {
        override fun onBind(homeData: HomeData?, position: Int) {
            val data = homeData?.regionItemData
            itemView.tv_confirmed.text = data?.confirmedForUI ?: "---"
            itemView.tv_active.text = data?.activeForUI ?: "---"
            itemView.tv_recovered.text = data?.recoveredForUI ?: "---"
            itemView.tv_deceased.text = data?.deathsForUI ?: "---"
            itemView.tv_data_timestamp.text = data?.lastupdatedtime ?: "---"
        }
    }


    class StateListViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<StateListData>(
        parent,
        R.layout.fragment_states
    ) {
        private val homeAdapter = StateListAdapter()

        override fun onBind(stateListData: StateListData?, position: Int) {
            val data = stateListData?.regionItemDataList
            setupRecyclerView()
            updateRecyclerView(data)
        }

        private fun setupRecyclerView() {
            itemView.rvCountryData.layoutManager = LinearLayoutManager(itemView.context)
            itemView.rvCountryData.adapter = homeAdapter
        }

        private fun updateRecyclerView(regionItemDataList: List<RegionItemData>?) {
            homeAdapter.refreshList(regionItemDataList)
        }
    }


    // TODO: Change Any to specific type
    class ResourcesViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<Any>(
        parent,
        R.layout.fragment_resource
    ) {
        override fun onBind(data: Any?, position: Int) {

        }
    }
}