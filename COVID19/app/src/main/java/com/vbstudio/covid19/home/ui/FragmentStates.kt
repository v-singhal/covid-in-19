package com.vbstudio.covid19.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbstudio.annotations.DaggerFragment
import com.vbstudio.covid19.R
import com.vbstudio.covid19.home.adapter.StateListAdapter
import com.vbstudio.covid19.home.dao.RegionItemData
import com.vbstudio.covid19.home.dao.StateListData
import com.vbstudio.covid19.home.model.HomeViewModel
import kotlinx.android.synthetic.main.fragment_states.*

@DaggerFragment
class FragmentStates : Fragment() {

    private val stateListAdapter = StateListAdapter()
    
    private lateinit var viewModel: HomeViewModel

    companion object {
        fun newInstance() = FragmentStates()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_states, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setupRecyclerView()
        getStateListData()
    }

    private fun setupRecyclerView() {
        rvCountryData.layoutManager = LinearLayoutManager(context)
        rvCountryData.adapter = stateListAdapter
    }

    private fun getStateListData() {
        viewModel.stateTabLiveData.observe(viewLifecycleOwner, Observer {
            updateStateListData(it)
        })
        viewModel.dataErrorLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(this@FragmentStates.context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateStateListData(stateListData: StateListData) {
        // TODO: Observe StateListData only
         updateUI(stateListData)
    }

    private fun updateUI(stateListData: StateListData?) {
        val data = stateListData?.regionItemDataList
        updateRecyclerView(data)
    }

    private fun updateRecyclerView(regionItemDataList: List<RegionItemData>?) {
        stateListAdapter.refreshList(regionItemDataList)
    }

}
