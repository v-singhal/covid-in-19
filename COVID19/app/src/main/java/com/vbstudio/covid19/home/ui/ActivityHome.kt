package com.vbstudio.covid19.home.ui

import android.os.Bundle
import com.vbstudio.annotations.DaggerActivity
import com.vbstudio.covid19.R
import com.vbstudio.covid19.core.ui.BaseActivity

@DaggerActivity
class ActivityHome : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    FragmentHome.newInstance()
                )
                .commitNow()
        }
    }
}
