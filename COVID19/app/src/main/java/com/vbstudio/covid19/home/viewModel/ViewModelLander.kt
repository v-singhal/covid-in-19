package com.vbstudio.covid19.home.viewModel

import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.repository.LanderRepository
import javax.inject.Inject

class ViewModelLander : ViewModel() {

    @Inject
    lateinit var landerRepository: LanderRepository

    enum class PageType {
        HOME,
        STATE_LIST,
        RESOURCE_LIST
    }

    init {
        Covid19Application.getAppComponent().inject(this)
    }

    fun initialiseHomeData() {
       return landerRepository.initialiseHomeData()
    }
}
