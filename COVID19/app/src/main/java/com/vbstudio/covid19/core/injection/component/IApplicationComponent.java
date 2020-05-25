package com.vbstudio.covid19.core.injection.component;

import com.vbstudio.covid19.Covid19Application;
import com.vbstudio.covid19.core.injection.AppModule;
import com.vbstudio.covid19.injector.modules.BuildersModule;
import com.vbstudio.covid19.injector.modules.BuildersModule_;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                /*
                App Module
                 */
                AppModule.class,
                BuildersModule.class,
                BuildersModule_.class
        })
public interface IApplicationComponent {

    void inject(Covid19Application application);
}
