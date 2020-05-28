package com.vbstudio.covid19.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vbstudio.annotations.DaggerFragment
import com.vbstudio.covid19.R
import com.vbstudio.covid19.home.dao.HomeData
import com.vbstudio.covid19.home.viewModel.ViewModelHome
import kotlinx.android.synthetic.main.fragment_home.*

@DaggerFragment
class FragmentHome : Fragment() {

    private lateinit var viewModelHome: ViewModelHome

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

        viewModelHome = ViewModelProvider(this).get(ViewModelHome::class.java)
        getHomeData()
    }

    private fun getHomeData() {
        viewModelHome.getHomeData().observe(viewLifecycleOwner, Observer {
            updateHomeData(it)
        })
//        viewModelHome.dataErrorLiveData.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(this@FragmentHome.context, it, Toast.LENGTH_SHORT).show()
//        })
    }

    private fun updateHomeData(homeData: HomeData) {
        updateUI(homeData)
    }

    private fun updateUI(homeData: HomeData?) {
        val data = homeData?.regionItemData
        tv_confirmed.text = data?.confirmedForUI ?: "---"
        tv_active.text = data?.activeForUI ?: "---"
        tv_recovered.text = data?.recoveredForUI ?: "---"
        tv_deceased.text = data?.deathsForUI ?: "---"
        tv_data_timestamp.text = data?.lastupdatedtimeForUI ?: "---"
    }

}
