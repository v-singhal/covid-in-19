package com.vbstudio.covid19.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vbstudio.annotations.DaggerFragment
import com.vbstudio.covid19.R

@DaggerFragment
class FragmentHome : Fragment() {

    private lateinit var viewModel: HomeViewModel

    companion object {
        fun newInstance() = FragmentHome()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        addObserver()
        getHomeData()
    }

    private fun addObserver() {
        viewModel.countryData.observe(viewLifecycleOwner, Observer { countryData ->

        })
    }

    private fun getHomeData() {
        viewModel.getHomeData()
    }

}
