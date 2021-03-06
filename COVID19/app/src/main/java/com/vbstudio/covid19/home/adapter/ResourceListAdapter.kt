package com.vbstudio.covid19.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.ui.adapter.BaseViewHolder
import com.vbstudio.covid19.home.dao.ResourceUIItem
import kotlinx.android.synthetic.main.item_resource_list_data.view.*
import kotlinx.android.synthetic.main.item_state_aon.view.tv_state_name

class ResourceListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var resourceList: ArrayList<ResourceUIItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ResourceViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ResourceViewHolder).onBind(resourceList[position], position)
    }

    override fun getItemCount(): Int {
        return resourceList.size ?: 0
    }

    fun refreshList(resourceList: ArrayList<ResourceUIItem>) {
        this.resourceList = resourceList;
        notifyDataSetChanged()
    }


    class ResourceViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<ResourceUIItem>(
        parent,
        R.layout.item_resource_list_data
    ) {

        override fun onCreate() {
        }

        override fun onBind(data: ResourceUIItem?, position: Int) {
            itemView.tv_state_name.text = data?.state
            itemView.container_resource_count.setCounter((data?.resourceItemDataList?.size?.toString()))
        }
    }
}