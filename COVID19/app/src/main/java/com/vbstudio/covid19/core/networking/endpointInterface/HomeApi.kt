package com.vbstudio.covid19.core.networking.endpointInterface

import com.vbstudio.covid19.home.dao.CountryData
import retrofit2.Call
import retrofit2.http.GET

interface HomeApi {

    @GET("/data.json")
    fun getCountryData(): Call<CountryData?>
}