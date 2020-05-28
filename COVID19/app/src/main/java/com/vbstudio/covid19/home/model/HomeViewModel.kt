package com.vbstudio.covid19.home.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.adapter.HomePagerAdapter
import com.vbstudio.covid19.home.dao.*
import com.vbstudio.covid19.home.viewModel.HomeDataModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var homeDataModel: HomeDataModel

    // LiveData used internally
    private val _dataErrorLiveData: MutableLiveData<Int> = MutableLiveData()
    private val _homeTabLiveData: MutableLiveData<HomeData> = MutableLiveData()
    private val _stateTabLiveData: MutableLiveData<StateListData> = MutableLiveData()
    private val _resourceTabLiveData: MutableLiveData<ResourceListData> = MutableLiveData()

    // Local Data used internally
    private val _homeFeedMap: HashMap<String, StateLatestData> = hashMapOf()

    // LiveData for public access
    val dataErrorLiveData = _dataErrorLiveData
    val homeTabLiveData = _homeTabLiveData
    val stateTabLiveData = _stateTabLiveData
    val resourceTabLiveData = _resourceTabLiveData

    init {
        Covid19Application.getAppComponent().inject(this)
    }

    fun getHomeData() {
        homeDataModel.getHomeData({
            GlobalScope.launch { processResponse(it) }
        }, { stringResId ->
            dataErrorLiveData.postValue(stringResId)
        })
    }

    private suspend fun processResponse(countryData: CountryData?) {
        GlobalScope.launch {
            populateHomeFeedMap(countryData)
            updateHomeLiveData();
        }
    }

    private fun populateHomeFeedMap(countryData: CountryData?) {
        countryData?.let {
            _homeFeedMap.clear()
            it.stateLatestData?.let { stateLatestDataList ->
                stateLatestDataList.forEach { stateData ->
                    stateData.statecode?.let { stateCode ->
                        _homeFeedMap[stateCode] = stateData
                    }
                }
            }
        }
    }

    private fun updateHomeLiveData() {
        val stateDataList = getStateDataList()

        _homeTabLiveData.postValue(
            HomeData(
                stateDataList[0],
                HomePagerAdapter.Companion.PageType.HOME.ordinal
            )
        )
        _stateTabLiveData.postValue(
            StateListData(
                stateDataList.subList(1, stateDataList.size - 1),
                HomePagerAdapter.Companion.PageType.STATE_LIST.ordinal
            )
        )
        _resourceTabLiveData.postValue(
            ResourceListData(
                HomePagerAdapter.Companion.PageType.RESOURCE_LIST.ordinal
            )
        )
    }

    private fun getStateDataList(): MutableList<RegionItemData> {
        val stateDataList: MutableList<RegionItemData> = mutableListOf()
        _homeFeedMap.forEach {
            stateDataList.add(RegionItemData(it.value))
        }
        stateDataList.sortByDescending { it.confirmed?.toInt() }

        return stateDataList
    }
}
