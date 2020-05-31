package com.vbstudio.covid19.home.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vbstudio.covid19.core.networking.ApiManager
import com.vbstudio.covid19.core.networking.interfaces.ResponseErrorListener
import com.vbstudio.covid19.core.networking.interfaces.ResponseListener
import com.vbstudio.covid19.core.repository.AppRepository
import com.vbstudio.covid19.home.dao.*
import com.vbstudio.covid19.home.viewModel.ViewModelLander
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LanderRepository(private val apiManager: ApiManager) : AppRepository() {

    private val HOME_CALL = "HomeApi"

    // LiveData used internally
    // private val _dataErrorLiveData: MutableLiveData<Int> = MutableLiveData()
    private val _homeTabLiveData: MutableLiveData<HomeData> = MutableLiveData()
    private val _stateTabLiveData: MutableLiveData<StateListData> = MutableLiveData()
    private val _resourceTabLiveData: MutableLiveData<ResourceListData> = MutableLiveData()

    // Local Data used internally
    private val _homeFeedMap: HashMap<String, StateLatestData> = hashMapOf()

    // LiveData for public access
    // val dataErrorLiveData = _dataErrorLiveData
    private val homeTabLiveData: LiveData<HomeData> = _homeTabLiveData
    private val stateTabLiveData: LiveData<StateListData> = _stateTabLiveData
    private val resourceTabLiveData: LiveData<ResourceListData> = _resourceTabLiveData

    fun getHomeData(): LiveData<HomeData> {
        if (!isValidDataInApp()) {
            getHomeDataFromApi()
        }
        return homeTabLiveData
    }

    fun getStateData(): LiveData<StateListData> {
        if (!isValidDataInApp()) {
            getHomeDataFromApi()
        }
        return stateTabLiveData
    }

    fun getResourcesData(): LiveData<ResourceListData> {
        if (!isValidDataInApp()) {
            getHomeDataFromApi()
        }
        return resourceTabLiveData
    }

    fun isValidDataInApp() = isValidLiveData(homeTabLiveData) && !apiManager.isCountryDataInProgress(HOME_CALL)

    private fun getHomeDataFromApi() {
        apiManager.getCountryData(
            object : ResponseListener<CountryData?> {
                override fun onResponse(response: CountryData?) {
                    GlobalScope.launch { processResponse(response) }
                }

            },
            object : ResponseErrorListener<CountryData?> {
                override fun onError(t: Throwable?) {
                    // dataErrorLiveData.postValue(R.string.check_network)
                }

                override fun onError(response: CountryData?, statusCode: Int) {
                    // dataErrorLiveData.postValue(R.string.server_error)
                }

            },
            HOME_CALL
        )
    }

    private suspend fun processResponse(countryData: CountryData?) {
        coroutineScope {
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
                ViewModelLander.PageType.HOME.ordinal
            )
        )
        _stateTabLiveData.postValue(
            StateListData(
                stateDataList.subList(1, stateDataList.size - 1),
                ViewModelLander.PageType.STATE_LIST.ordinal
            )
        )
        _resourceTabLiveData.postValue(
            ResourceListData(
                ViewModelLander.PageType.RESOURCE_LIST.ordinal
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