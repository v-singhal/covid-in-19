package com.vbstudio.covid19.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.vbstudio.annotations.DaggerFragment
import com.vbstudio.covid19.R
import com.vbstudio.covid19.home.adapter.HomePagerAdapter
import com.vbstudio.covid19.home.dao.HomeBaseData
import com.vbstudio.covid19.home.model.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@DaggerFragment
class FragmentHome : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private val homePagerAdapter = HomePagerAdapter()

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
        setupViewPager()
        getHomeData()
    }

    private fun setupViewPager() {

        view_pager_root.adapter = homePagerAdapter

        view_pager_root.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateBottomNavigation(position)
            }
        })

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            updateViewPagerPosition(item)
            true
        }
    }

    private fun updateBottomNavigation(position: Int) {
        bottom_navigation.selectedItemId = bottom_navigation.menu.getItem(position).itemId
    }

    private fun updateViewPagerPosition(item: MenuItem) {
        GlobalScope.launch {
            view_pager_root.setCurrentItem(
                (bottom_navigation.menu as MenuBuilder)
                    .findItemIndex(
                        item.itemId
                    ), true
            )
        }
    }

    private fun getHomeData() {
        viewModel.getHomeData()
        viewModel.homeLiveData.observe(viewLifecycleOwner, Observer {
            updateViewPager(it)
        })
        viewModel.dataErrorLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(this@FragmentHome.context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun updateViewPager(tabDataList: List<HomeBaseData>?) {
        homePagerAdapter.refreshList(tabDataList)
    }

}
