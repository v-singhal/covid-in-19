package com.vbstudio.covid19.home.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.adapter.HomePagerAdapter
import com.vbstudio.covid19.home.dao.*
import com.vbstudio.covid19.home.viewModel.HomeDataModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var homeDataModel: HomeDataModel

    // LiveData used internally
    private val _dataErrorLiveData: MutableLiveData<Int> = MutableLiveData()
    private val _homeLiveData: MutableLiveData<List<HomeBaseData>> = MutableLiveData()

    // Local Data used internally
    private val _homeFeedMap: HashMap<String, StateLatestData> = hashMapOf()

    // LiveData for public access
    val dataErrorLiveData = _dataErrorLiveData
    val homeLiveData = _homeLiveData

    init {
        Covid19Application.getAppComponent().inject(this)
    }

    fun getHomeData() {
        homeDataModel.getHomeData({
            viewModelScope.launch { processResponse(it) }
        }, { stringResId ->
            dataErrorLiveData.postValue(stringResId)
        })
    }

    private suspend fun processResponse(countryData: CountryData?) {
        viewModelScope.launch {
            populateHomeFeedMap(countryData)
        }.run {
            // Create master list for recyclerview
            viewModelScope.launch {
                updateHomeLiveData();
            }
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
        val homeDataList = mutableListOf<HomeBaseData>()
        val stateDataList = getStateDataList()

        addTabData(
            homeDataList,
            HomeData(stateDataList[0], HomePagerAdapter.Companion.PageType.HOME.ordinal),
            HomePagerAdapter.Companion.PageType.HOME.ordinal
        );
        addTabData(
            homeDataList,
            StateListData(stateDataList, HomePagerAdapter.Companion.PageType.STATE_LIST.ordinal),
            HomePagerAdapter.Companion.PageType.STATE_LIST.ordinal
        );
        addTabData(
            homeDataList,
            ResourceListData(HomePagerAdapter.Companion.PageType.RESOURCE_LIST.ordinal),
            HomePagerAdapter.Companion.PageType.RESOURCE_LIST.ordinal
        );

        _homeLiveData.postValue(homeDataList)
    }

    private fun getStateDataList(): MutableList<RegionItemData> {
        val stateDataList: MutableList<RegionItemData> = mutableListOf()
        _homeFeedMap.forEach {
            stateDataList.add(RegionItemData(it.value))
        }
        stateDataList.sortByDescending { it.confirmed?.toInt() }

        return stateDataList
    }

    private fun addTabData(
        homeDataList: MutableList<HomeBaseData>,
        homeDataToInsert: HomeBaseData,
        dataPosition: Int
    ) {
        homeDataList.add(dataPosition, homeDataToInsert)
    }
}
