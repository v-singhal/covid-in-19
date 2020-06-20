package com.vbstudio.covid19.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vbstudio.annotations.DaggerFragment
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.utils.UIUtils
import com.vbstudio.covid19.home.dao.HomeData
import com.vbstudio.covid19.home.dao.StateListData
import com.vbstudio.covid19.home.viewModel.ViewModelHome
import kotlinx.android.synthetic.main.fragment_home.*

@DaggerFragment
class FragmentHome : Fragment() {

    private val SCALING_FACTOR: Float = 0.6F
    private val ROW_COUNT: Int = 2

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateHeaderHeight()
        container_confirmed.setupAsHeader()
        container_active.setupAsHeader()
        container_recovered.setupAsHeader()
        container_deceased.setupAsHeader()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelHome = ViewModelProvider(this).get(ViewModelHome::class.java)
        getHomeData()
    }

    private fun updateHeaderHeight() {
        val screenHeight = UIUtils.getDisplayHeight(context)
        screenHeight?.let {
            val componentHeight = (it * SCALING_FACTOR / ROW_COUNT).toInt()
            container_confirmed.layoutParams.height = componentHeight
            container_active.layoutParams.height = componentHeight
            container_recovered.layoutParams.height = componentHeight
            container_deceased.layoutParams.height = componentHeight
        }
    }

    private fun getHomeData() {
        viewModelHome.getHomeData().observe(viewLifecycleOwner, Observer {
            updateHomeData(it)
        })
        viewModelHome.getTopRecoveries().observe(viewLifecycleOwner, Observer {
            updateTopRecoveries(it)
        })
        viewModelHome.getTopActiveCases().observe(viewLifecycleOwner, Observer {
            updateTopActiveCases(it)
        })
//        viewModelHome.dataErrorLiveData.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(this@FragmentHome.context, it, Toast.LENGTH_SHORT).show()
//        })
    }

    private fun updateHomeData(homeData: HomeData) {
        val data = homeData.regionItemData
        container_confirmed.setCounter(data.confirmedForUI)
        container_active.setCounter(data.activeForUI)
        container_recovered.setCounter(data.recoveredForUI)
        container_deceased.setCounter(data.deathsForUI)
        tv_data_timestamp.text = data.lastupdatedtimeForUI ?: "---"
    }

    private fun updateTopRecoveries(stateList: StateListData?) {
        updateTopSectionView(
            stateList,
            top_stats_recovered,
            "Top Recovered Cases",
            StatsSectionView.StatsType.Recovered
        )
    }

    private fun updateTopActiveCases(stateList: StateListData?) {
        updateTopSectionView(
            stateList,
            top_stats_active,
            "Top Active Cases",
            StatsSectionView.StatsType.Active
        )
    }

    private fun updateTopSectionView(
        stateList: StateListData?,
        topSectionView: TopStatsList,
        sectionHeader: String,
        statsType: StatsSectionView.StatsType
    ) {
        stateList?.regionItemDataList?.let {
            topSectionView.visibility = VISIBLE
            topSectionView.setupList(
                sectionHeader,
                it,
                statsType
            )
        }
    }

}
