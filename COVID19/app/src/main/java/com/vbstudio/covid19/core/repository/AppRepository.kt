package com.vbstudio.covid19.core.repository

import androidx.lifecycle.LiveData
import com.vbstudio.covid19.home.dao.HomeData

abstract class AppRepository {

    protected fun isValidData(liveData: LiveData<HomeData>): Boolean {
        return liveData.value != null
    }
}