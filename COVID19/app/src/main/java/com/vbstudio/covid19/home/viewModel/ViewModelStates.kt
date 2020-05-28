package com.vbstudio.covid19.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.dao.StateListData
import com.vbstudio.covid19.home.repository.LanderRepository
import javax.inject.Inject

class ViewModelStates : ViewModel() {

    @Inject
    lateinit var landerRepository: LanderRepository

    init {
        Covid19Application.getAppComponent().inject(this)
    }

    fun getStates(): LiveData<StateListData> {
       return landerRepository.getStateData()
    }
}
