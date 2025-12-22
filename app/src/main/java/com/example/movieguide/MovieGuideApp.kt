package com.example.movieguide

import android.app.Application
import com.example.common.di.Dependencies
import com.example.common.di.DependenciesProvider
import com.example.movieguide.di.AppComponent
import com.example.movieguide.di.DaggerAppComponent


class MovieGuideApp : Application(), DependenciesProvider {
    val appComponent: AppComponent = DaggerAppComponent.factory().create(this)

    override fun getDependencies(): Dependencies {
        return appComponent
    }
}


