package com.vbstudio.covid19.home.viewModel

import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.networking.ApiManager
import com.vbstudio.covid19.core.networking.interfaces.ResponseErrorListener
import com.vbstudio.covid19.core.networking.interfaces.ResponseListener
import com.vbstudio.covid19.home.dao.CountryData

class HomeDataModel(private val apiManager: ApiManager) {

    fun getHomeData(successDelegate: ((CountryData?) -> Unit), errorDelegate: ((Int) -> Unit)) {
        apiManager.getCountryData(
            object : ResponseListener<CountryData?> {
                override fun onResponse(response: CountryData?) {
                    successDelegate.invoke(response)
                }

            },
            object : ResponseErrorListener<CountryData?> {
                override fun onError(t: Throwable?) {
                    errorDelegate.invoke(R.string.check_network)
                }

                override fun onError(response: CountryData?, statusCode: Int) {
                    errorDelegate.invoke(R.string.server_error)
                }

            })
    }
}