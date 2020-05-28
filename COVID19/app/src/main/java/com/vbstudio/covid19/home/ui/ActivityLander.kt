package com.vbstudio.covid19.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vbstudio.annotations.DaggerActivity
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.ui.BaseActivity
import com.vbstudio.covid19.core.utils.AnimationUtils
import com.vbstudio.covid19.home.dao.HomeData
import com.vbstudio.covid19.home.viewModel.ViewModelLander
import kotlinx.android.synthetic.main.activity_lander.*

@DaggerActivity
class ActivityLander : BaseActivity(), FragmentManager.OnBackStackChangedListener {

    private lateinit var viewModelLander: ViewModelLander

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lander)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupView(savedInstanceState)
    }

    override fun onBackStackChanged() {
//        TODO("Not yet implemented")
    }

    private fun setupView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModelLander = ViewModelProvider(this).get(ViewModelLander::class.java)
            getHomeData()
        }
        setupFragmentContainer()
    }

    private fun setupFragmentContainer() {
        supportFragmentManager.addOnBackStackChangedListener(this)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            switchFragments(item.itemId)
            true
        }
        bottom_navigation.selectedItemId = R.id.home
    }

    private fun switchFragments(itemId: Int) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val fragment: Fragment? = getFragmentAt(itemId)

        fragment?.let {
            val fragmentTag: String = fragment.javaClass.simpleName
            fragmentTransaction.replace(R.id.container_nav_fragments, it, fragmentTag)
            fragmentTransaction.addToBackStack(fragmentTag)
            fragmentTransaction.commit()
            AnimationUtils.revealView(container_nav_fragments)
        }
    }

    private fun getFragmentAt(itemId: Int): Fragment? {
        return when (itemId) {
            R.id.home -> {
                FragmentHome()
            }
            R.id.state_list -> {
                FragmentStates()
            }
            R.id.resources -> {
                FragmentResources()
            }
            else -> {
                null
            }
        }

    }

    private fun getHomeData() {
        viewModelLander.getHomeData().observe(this, Observer {
            updateViewPager(it)
        })
//        viewModelLander.dataErrorLiveData.observe(this, Observer {
//            Toast.makeText(this@ActivityLander, it, Toast.LENGTH_SHORT).show()
//        })
    }

    private fun updateViewPager(it: HomeData?) {

    }
}
