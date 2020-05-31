package com.vbstudio.covid19.core.repository

import androidx.lifecycle.LiveData

abstract class AppRepository {

    protected fun isValidLiveData(liveData: LiveData<*>): Boolean {
        return liveData.value != null
    }
}