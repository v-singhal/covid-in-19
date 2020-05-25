package com.vbstudio.covid19.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.core.networking.ApiManager
import com.vbstudio.covid19.core.networking.interfaces.ResponseErrorListener
import com.vbstudio.covid19.core.networking.interfaces.ResponseListener
import com.vbstudio.covid19.home.dao.CountryData
import javax.inject.Inject

class HomeViewModel : ViewModel() {
    @Inject
    lateinit var apiManager: ApiManager

    private val _countryData: MutableLiveData<CountryData> = MutableLiveData()
    val countryData = _countryData

    init {
        Covid19Application.getAppComponent().inject(this)
    }

    fun getHomeData() {
        apiManager.getNationData(
            object : ResponseListener<CountryData?> {
                override fun onResponse(response: CountryData?) {
                    _countryData.postValue(response)
                }

            },
            object : ResponseErrorListener<CountryData?> {
                override fun onError(t: Throwable?) {

                }

                override fun onError(response: CountryData?, statusCode: Int) {

                }

            })
    }
}
