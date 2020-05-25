package com.vbstudio.covid19.home.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.dao.CountryData
import com.vbstudio.covid19.home.dao.HomeFeedData
import com.vbstudio.covid19.home.dao.StateLatestData
import com.vbstudio.covid19.home.viewModel.HomeDataModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    @Inject
    lateinit var homeDataModel: HomeDataModel

    // LiveData used internally
    private val _dataErrorLiveData: MutableLiveData<Int> = MutableLiveData()
    private val _homeFeedLiveData: MutableLiveData<List<HomeFeedData>> = MutableLiveData()

    // Local Data used internally
    private val _homeFeedMap: HashMap<String, StateLatestData> = hashMapOf()

    // LiveData for public access
    val dataErrorLiveData = _dataErrorLiveData
    val homeFeedLiveData = _homeFeedLiveData

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
                updateHomeFeedLiveData();
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

    private fun updateHomeFeedLiveData() {
        val stateDataList: MutableList<HomeFeedData> = mutableListOf()
        _homeFeedMap.forEach {
            stateDataList.add(HomeFeedData(it.value))
        }

        _homeFeedLiveData.postValue(stateDataList)
    }
}
