package com.example.marti.spacex.di

import com.example.marti.spacex.ui.detaillaunches.DetailFragment
import com.example.marti.spacex.ui.launcheslist.LaunchesFragment
import com.example.marti.spacex.ui.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeLaunchesFragment(): LaunchesFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment
}