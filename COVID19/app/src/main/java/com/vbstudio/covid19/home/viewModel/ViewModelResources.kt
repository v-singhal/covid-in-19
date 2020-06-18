package com.vbstudio.covid19.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.home.dao.ResourceUIItem
import com.vbstudio.covid19.home.repository.LanderRepository
import javax.inject.Inject

class ViewModelResources : ViewModel() {

    @Inject
    lateinit var landerRepository: LanderRepository

    init {
        Covid19Application.getAppComponent().inject(this)
    }

    fun getResources(): LiveData<ArrayList<ResourceUIItem>> {
        return Transformations.switchMap(landerRepository.resourceTabLiveData) {
            val resourceTabLiveData: MutableLiveData<ArrayList<ResourceUIItem>> = MutableLiveData()
            val resourceUIList: ArrayList<ResourceUIItem> = arrayListOf()
            it.iterator().forEach {
                resourceUIList.add(
                    ResourceUIItem(
                        it.key,
                        it.value
                    )
                )
            }

            resourceUIList.sortByDescending { resourceUIItem ->
                resourceUIItem.resourceItemDataList.size
            }

            resourceTabLiveData.postValue(resourceUIList)
            resourceTabLiveData
        }
    }
}
