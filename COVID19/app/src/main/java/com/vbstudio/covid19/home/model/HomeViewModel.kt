package com.vbstudio.covid19.home.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.dao.CountryData
import com.vbstudio.covid19.home.viewModel.HomeDataModel
import javax.inject.Inject

class HomeViewModel : ViewModel() {
    @Inject
    lateinit var homeDataModel: HomeDataModel

    private val _countryDataLiveData: MutableLiveData<CountryData> = MutableLiveData()
    private val _dataErrorLiveData: MutableLiveData<Int> = MutableLiveData()

    val countryDataLiveData = _countryDataLiveData
    val dataErrorLiveData = _dataErrorLiveData

    init {
        Covid19Application.getAppComponent().inject(this)
    }

    fun getHomeData() {
        homeDataModel.getHomeData({ countryDataFromResponse ->
            _countryDataLiveData.postValue(countryDataFromResponse)
        }, { stringResId ->
            dataErrorLiveData.postValue(stringResId)
        })
    }
}
