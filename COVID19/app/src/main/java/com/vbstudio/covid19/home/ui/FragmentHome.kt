package com.vbstudio.covid19.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vbstudio.annotations.DaggerFragment
import com.vbstudio.covid19.R
import com.vbstudio.covid19.home.dao.HomeData
import com.vbstudio.covid19.home.model.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

@DaggerFragment
class FragmentHome : Fragment() {

    private lateinit var viewModel: HomeViewModel

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
        getHomeData()
    }

    private fun getHomeData() {
        viewModel.homeTabLiveData.observe(viewLifecycleOwner, Observer {
            updateHomeData(it)
        })
        viewModel.dataErrorLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(this@FragmentHome.context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateHomeData(homeData: HomeData) {
        // TODO: Observe HomeData only
         updateUI(homeData)
    }

    private fun updateUI(homeData: HomeData?) {
        val data = homeData?.regionItemData
        tv_confirmed.text = data?.confirmedForUI ?: "---"
        tv_active.text = data?.activeForUI ?: "---"
        tv_recovered.text = data?.recoveredForUI ?: "---"
        tv_deceased.text = data?.deathsForUI ?: "---"
        tv_data_timestamp.text = data?.lastupdatedtime ?: "---"
    }

}
