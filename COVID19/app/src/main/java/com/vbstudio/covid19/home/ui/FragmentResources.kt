package com.vbstudio.covid19.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbstudio.annotations.DaggerFragment
import com.vbstudio.covid19.R
import com.vbstudio.covid19.home.adapter.ResourceListAdapter
import com.vbstudio.covid19.home.dao.ResourceUIItem
import com.vbstudio.covid19.home.viewModel.ViewModelResources
import kotlinx.android.synthetic.main.fragment_resource.*

@DaggerFragment
class FragmentResources : Fragment() {

    private val resourceListAdapter = ResourceListAdapter()

    private lateinit var viewModelResources: ViewModelResources

    companion object {
        fun newInstance() = FragmentResources()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_resource, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelResources = ViewModelProvider(this).get(ViewModelResources::class.java)
        setupRecyclerView()
        getResourceListData()
    }

    private fun setupRecyclerView() {
        rvResourceData.layoutManager = LinearLayoutManager(context)
        rvResourceData.adapter = resourceListAdapter
        rvResourceData.setHasFixedSize(true)
    }

    private fun getResourceListData() {
        viewModelResources.getResources().observe(viewLifecycleOwner, Observer {
            updateStateListData(it)
        })
//        viewModelResources.dataErrorLiveData.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(this@FragmentResources.context, it, Toast.LENGTH_SHORT).show()
//        })
    }

    private fun updateStateListData(resourceList: ArrayList<ResourceUIItem>) {
         updateUI(resourceList)
    }

    private fun updateUI(resourceList: ArrayList<ResourceUIItem>) {
        resourceListAdapter.refreshList(resourceList)
    }

}
