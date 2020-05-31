package com.vbstudio.covid19.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.vbstudio.annotations.DaggerActivity
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.ui.BaseActivity
import com.vbstudio.covid19.core.ext.revealView
import com.vbstudio.covid19.home.viewModel.ViewModelLander
import kotlinx.android.synthetic.main.activity_lander.*

@DaggerActivity
class ActivityLander : BaseActivity() {

    private lateinit var viewModelLander: ViewModelLander

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lander)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setupView(savedInstanceState)
    }

    private fun setupView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModelLander = ViewModelProvider(this).get(ViewModelLander::class.java)
            getHomeData()
        }
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
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
            fragmentTransaction.commit()
            container_nav_fragments.revealView()
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
        viewModelLander.getHomeData()
//        viewModelLander.dataErrorLiveData.observe(this, Observer {
//            Toast.makeText(this@ActivityLander, it, Toast.LENGTH_SHORT).show()
//        })
    }
}
