package com.vbstudio.covid19

import android.app.Activity
import android.app.Application
import com.vbstudio.covid19.core.injection.AppModule
import com.vbstudio.covid19.core.injection.component.DaggerIApplicationComponent
import com.vbstudio.covid19.core.injection.component.IApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class Covid19Application: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    companion object {
        lateinit var applicationComponent: IApplicationComponent

        fun getAppComponent(): IApplicationComponent {
            return applicationComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    private fun setupDagger() {
        applicationComponent = DaggerIApplicationComponent.builder().appModule(AppModule(this)).build()
        applicationComponent.inject(this)
    }

}