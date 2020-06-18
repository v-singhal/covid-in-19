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

    // Local Data used internally
    private val _homeFeedMap: HashMap<String, StateLatestData> = hashMapOf()

    // LiveData used internally
    private val _masterLiveData: MutableLiveData<StateListData> = MutableLiveData()
    private val _resourceTabLiveData: MutableLiveData<ResourceListData> = MutableLiveData()
    // private val _dataErrorLiveData: MutableLiveData<Int> = MutableLiveData()

    // LiveData for public access
    val masterLiveData: LiveData<StateListData> = _masterLiveData
    val resourceTabLiveData: LiveData<ResourceListData> = _resourceTabLiveData
    // val dataErrorLiveData = _dataErrorLiveData

    fun initialiseHomeData() {
        if (!isValidDataInApp()) {
            getHomeDataFromApi()
        }
    }

fun isValidDataInApp() =
        isValidLiveData(masterLiveData) && !apiManager.isCountryDataInProgress(HOME_CALL)

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
            launch {
                populateHomeFeedMap(countryData)
                updateHomeLiveData()
            }
        }.invokeOnCompletion {
            // dataErrorLiveData.postValue(it?.localizedMessage)
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

        _masterLiveData.postValue(
            StateListData(
                stateDataList,
                ViewModelLander.PageType.STATE_LIST.ordinal
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