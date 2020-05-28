package com.vbstudio.covid19.core.injection.component;

import com.vbstudio.covid19.Covid19Application;
import com.vbstudio.covid19.core.injection.AppModule;
import com.vbstudio.covid19.home.viewModel.ViewModelHome;
import com.vbstudio.covid19.home.viewModel.ViewModelLander;
import com.vbstudio.covid19.home.viewModel.ViewModelResources;
import com.vbstudio.covid19.home.viewModel.ViewModelStates;
import com.vbstudio.covid19.injector.modules.BuildersModule;
import com.vbstudio.covid19.injector.modules.BuildersModule_;

import org.jetbrains.annotations.NotNull;

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

    void inject(@NotNull Covid19Application application);

    void inject(@NotNull ViewModelLander viewModelLander);

    void inject(@NotNull ViewModelHome viewModelHome);

    void inject(@NotNull ViewModelStates viewModelStates);

    void inject(@NotNull ViewModelResources viewModelResources);
}
