package com.vbstudio.covid19.home

import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.core.networking.ApiManager
import javax.inject.Inject

class HomeViewModel : ViewModel() {
    @Inject
    lateinit var apiManager: ApiManager

    init {
        Covid19Application.getAppComponent().inject(this)
    }
}
