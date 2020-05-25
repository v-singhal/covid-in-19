package com.vbstudio.covid19.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vbstudio.annotations.DaggerFragment
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.utils.LogUtils

@DaggerFragment
class FragmentHome : Fragment() {

    private val LOG_TAG: String? = javaClass.simpleName

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
        viewModel.getHomeData()
        viewModel.countryData.observe(viewLifecycleOwner, Observer { countryData ->
            LogUtils.d(
                LOG_TAG,
                "countryDailyData: " + countryData.countryDailyData?.size
                        + " countryDayTestingTimelineData: " + countryData.countryDayTestingTimelineData?.size
                        + " stateLatestData: " + countryData.stateLatestData?.size
            )
        })
    }

}
