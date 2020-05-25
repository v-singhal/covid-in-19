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
import com.vbstudio.covid19.home.adapter.HomeAdapter
import com.vbstudio.covid19.home.dao.HomeFeedData
import com.vbstudio.covid19.home.model.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

@DaggerFragment
class FragmentHome : Fragment() {

    enum class FeedRowType {
        COUNTRY,
        STATE
    }

    private lateinit var viewModel: HomeViewModel
    private val homeAdapter = HomeAdapter()

    companion object {
        fun newInstance() = FragmentHome()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setupRecyclerView()
        getHomeData()
    }

    private fun setupRecyclerView() {
        rvCountryData.layoutManager = LinearLayoutManager(context)
        rvCountryData.adapter = homeAdapter
    }

    private fun getHomeData() {
        viewModel.getHomeData()
        viewModel.homeFeedLiveData.observe(viewLifecycleOwner, Observer {
            updateRecyclerView(it)
        })
        viewModel.dataErrorLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(this@FragmentHome.context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateRecyclerView(homeFeedDataList: List<HomeFeedData>?) {
        homeAdapter.refreshList(homeFeedDataList)
    }

}
