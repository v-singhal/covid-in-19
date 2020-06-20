package com.vbstudio.covid19.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.adapter.StateListAdapter
import com.vbstudio.covid19.home.dao.HomeData
import com.vbstudio.covid19.home.dao.StateListData
import com.vbstudio.covid19.home.repository.LanderRepository
import com.vbstudio.covid19.home.utils.StateDataUtils
import javax.inject.Inject

class ViewModelHome : ViewModel() {

    private val TOP_STATS_COUNT: Int = 5

    @Inject
    lateinit var landerRepository: LanderRepository

    init {
        Covid19Application.getAppComponent().inject(this)
    }

    fun getHomeData(): LiveData<HomeData> {
        return Transformations.switchMap(landerRepository.masterLiveData) {
            val homeTabLiveData: MutableLiveData<HomeData> = MutableLiveData()
            val homeData = HomeData(
                it.regionItemDataList.first { regionItemData ->
                    regionItemData.type == StateListAdapter.Companion.FeedRowType.COUNTRY
                },
                StateListAdapter.Companion.FeedRowType.COUNTRY.ordinal
            )
            homeTabLiveData.postValue(homeData)
            homeTabLiveData
        }
    }

    fun getTopRecoveries(): LiveData<StateListData> {
        return Transformations.switchMap(landerRepository.masterLiveData)
        {
            val stateListLiveData: MutableLiveData<StateListData> = MutableLiveData()
            val stateListData = StateListData(
                StateDataUtils.getStatesFromList(it.regionItemDataList)
                    .sortedByDescending {
                        it.recovered?.toInt()
                    }
                    .subList(0, TOP_STATS_COUNT),
                StateListAdapter.Companion.FeedRowType.STATE.ordinal
            )
            stateListLiveData.postValue(stateListData)
            stateListLiveData
        }
    }

    fun getTopActiveCases(): LiveData<StateListData> {
        return Transformations.switchMap(landerRepository.masterLiveData)
        {
            val stateListLiveData: MutableLiveData<StateListData> = MutableLiveData()
            val stateListData = StateListData(
                StateDataUtils.getStatesFromList(it.regionItemDataList)
                    .sortedByDescending {
                        it.active?.toInt()
                    }
                    .subList(0, TOP_STATS_COUNT),
                StateListAdapter.Companion.FeedRowType.STATE.ordinal
            )
            stateListLiveData.postValue(stateListData)
            stateListLiveData
        }
    }
}
