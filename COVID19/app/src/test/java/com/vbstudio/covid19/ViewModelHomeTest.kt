package com.vbstudio.covid19

import androidx.lifecycle.Observer
import com.vbstudio.covid19.home.dao.HomeData
import com.vbstudio.covid19.home.repository.LanderRepository
import com.vbstudio.covid19.home.viewModel.ViewModelHome
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ViewModelHomeTest {

    @Mock
    lateinit var landerRepository: LanderRepository

    @Mock
    lateinit var observer: Observer<HomeData>


    private lateinit var viewModelHome: ViewModelHome

    @Before
    private fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModelHome = Mockito.spy(ViewModelHome())
    }

    @Test
    private fun test_filterResultValid() {
        viewModelHome.getHomeData().observeForever(observer)
    }

}
