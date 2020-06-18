package com.vbstudio.covid19.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.adapter.StateListAdapter
import com.vbstudio.covid19.home.dao.HomeData
import com.vbstudio.covid19.home.repository.LanderRepository
import javax.inject.Inject

class ViewModelHome : ViewModel() {

    @Inject
    lateinit var landerRepository: LanderRepository

    init {
        Covid19Application.getAppComponent().inject(this)
    }

    fun getHomeData(): LiveData<HomeData> {
        return Transformations.switchMap(landerRepository.masterLiveData) {
            val homeTabLiveData: MutableLiveData<HomeData> = MutableLiveData()
            val homeData = HomeData(
                it.regionItemDataList.first {regionItemData ->
                    regionItemData.type == StateListAdapter.Companion.FeedRowType.COUNTRY
                },
                StateListAdapter.Companion.FeedRowType.COUNTRY.ordinal
            )
            homeTabLiveData.postValue(homeData)
            homeTabLiveData
        }
    }
}
