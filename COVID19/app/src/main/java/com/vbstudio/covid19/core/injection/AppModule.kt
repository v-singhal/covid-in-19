package com.vbstudio.covid19.core.injection

import com.vbstudio.covid19.Covid19Application
import com.vbstudio.covid19.core.networking.ApiManager
import com.vbstudio.covid19.core.networking.BaseRetrofitBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Covid19Application) {

    @Provides
    @Singleton
    fun provideApplication(): Covid19Application {
        return application
    }

    @Provides
    @Singleton
    fun provideBaseRetrofitBuilder(application: Covid19Application): BaseRetrofitBuilder {
        return BaseRetrofitBuilder(application)
    }

    @Provides
    @Singleton
    fun provideApiManager(baseRetrofitBuilder: BaseRetrofitBuilder): ApiManager {
        return ApiManager(baseRetrofitBuilder)
    }
}