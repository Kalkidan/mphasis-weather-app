package com.mphasis.weatherapplication.di

import android.content.Context
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

/**
 * This will be used to inject different utility
 * sdk instances that we use for the application
 */
@Module
@InstallIn(ActivityComponent::class)
class MphUtilityModules {

    @Provides
    @ActivityScoped
    fun providesFusedLocationProviderClient(@ApplicationContext context: Context) =
        LocationServices.getFusedLocationProviderClient(context)

}