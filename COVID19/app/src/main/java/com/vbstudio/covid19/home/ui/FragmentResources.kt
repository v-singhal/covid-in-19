package com.vbstudio.covid19.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vbstudio.annotations.DaggerFragment
import com.vbstudio.covid19.R
import com.vbstudio.covid19.home.dao.ResourceListData
import com.vbstudio.covid19.home.viewModel.ViewModelResources

@DaggerFragment
class FragmentResources : Fragment() {

    private lateinit var viewModelResources: ViewModelResources

    companion object {
        fun newInstance() = FragmentResources()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_resource, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelResources = ViewModelProvider(this).get(ViewModelResources::class.java)
        getResourceListData()
    }

    private fun getResourceListData() {
        viewModelResources.getResources().observe(viewLifecycleOwner, Observer {
            updateStateListData(it)
        })
//        viewModelResources.dataErrorLiveData.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(this@FragmentResources.context, it, Toast.LENGTH_SHORT).show()
//        })
    }

    private fun updateStateListData(resourceListData: ResourceListData?) {
         updateUI(resourceListData)
    }

    private fun updateUI(resourceLisData: ResourceListData?) {
        // TODO: Update UI
    }

}
