package com.vbstudio.covid19.core.networking.endpointInterface

import com.vbstudio.covid19.home.dao.CountryData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Tag

interface HomeApi {

    @GET("/data.json")
    fun getCountryData(@Tag tag: String): Call<CountryData?>
}