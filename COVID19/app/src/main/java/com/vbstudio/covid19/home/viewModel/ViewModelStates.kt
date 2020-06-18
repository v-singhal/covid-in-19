package com.vbstudio.covid19.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.adapter.StateListAdapter
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
        return Transformations.switchMap(landerRepository.masterLiveData)
        {
            val stateListLiveData: MutableLiveData<StateListData> = MutableLiveData()
            val stateListData = StateListData(
                it.regionItemDataList.filter { regionItemData ->
                    regionItemData.type == StateListAdapter.Companion.FeedRowType.STATE
                }, StateListAdapter.Companion.FeedRowType.STATE.ordinal
            )
            stateListLiveData.postValue(stateListData)
            stateListLiveData
        }
    }
}
