package com.vbstudio.covid19.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vbstudio.covid19.R

class ActivityHome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentHome.newInstance())
                .commitNow()
        }
    }
}
