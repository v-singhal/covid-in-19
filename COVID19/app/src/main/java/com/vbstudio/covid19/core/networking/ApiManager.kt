package com.vbstudio.covid19.core.networking

import com.vbstudio.covid19.core.networking.endpointInterface.HomeApi
import com.vbstudio.covid19.core.networking.interfaces.ResponseErrorListener
import com.vbstudio.covid19.core.networking.interfaces.ResponseListener
import com.vbstudio.covid19.home.dao.CountryData
import com.vbstudio.covid19.home.dao.ResourceListData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiManager(private val baseRetrofitBuilder: BaseRetrofitBuilder) {
    private val CALL_FAILURE_TEXT = "Canceled"
    private val covidApiBlobBaseAddress: String = "https://api.covid19india.org"


    fun getCountryData(
        responseListener: ResponseListener<CountryData?>,
        errorListener: ResponseErrorListener<CountryData?>,
        tag: String
    ) {
        baseRetrofitBuilder
            .build(covidApiBlobBaseAddress)
            .create(HomeApi::class.java)
            .getCountryData(tag)
            .run {
                enqueue(object : Callback<CountryData?> {
                    override fun onResponse(
                        call: Call<CountryData?>,
                        response: Response<CountryData?>
                    ) {
                        when {
                            response.body() != null -> {
                                responseListener.onResponse(response.body())
                            }
                            else -> {
                                errorListener.onError(response.body(), response.code())
                            }
                        }
                    }

                    override fun onFailure(call: Call<CountryData?>, t: Throwable) {
                        errorListener.onError(t)
                    }
                })
            }
    }

    fun isCountryDataInProgress(tag: String): Boolean {
        return baseRetrofitBuilder.isApiCallInProgress(tag)
    }

    fun getResourceData(
        responseListener: ResponseListener<ResourceListData?>,
        errorListener: ResponseErrorListener<ResourceListData?>,
        tag: String
    ) {
        baseRetrofitBuilder
            .build(covidApiBlobBaseAddress)
            .create(HomeApi::class.java)
            .getResourceData(tag)
            .run {
                enqueue(object : Callback<ResourceListData?> {
                    override fun onResponse(
                        call: Call<ResourceListData?>,
                        response: Response<ResourceListData?>
                    ) {
                        when {
                            response.body() != null -> {
                                responseListener.onResponse(response.body())
                            }
                            else -> {
                                errorListener.onError(response.body(), response.code())
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResourceListData?>, t: Throwable) {
                        errorListener.onError(t)
                    }
                })
            }
    }
}