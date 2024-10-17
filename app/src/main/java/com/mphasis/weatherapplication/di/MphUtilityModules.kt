package com.mphasis.weatherapplication.di

import android.content.Context
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.util.DebugLogger
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import javax.inject.Singleton

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

@Module
@InstallIn(SingletonComponent::class)
class ImageLoaderModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        // We specifically request dagger.Lazy here, so that it's not instantiated from Dagger.
        okHttpCallFactory: dagger.Lazy<Call.Factory>,
        @ApplicationContext application: Context,
    ): ImageLoader {
        return ImageLoader.Builder(application)
            .callFactory { okHttpCallFactory.get() }
            .components { add(SvgDecoder.Factory()) }
            // Assume most content images are versioned urls
            // but some problematic images are fetching each time
            .respectCacheHeaders(false)
            .apply {
                logger(DebugLogger())
            }
            .build()
    }
}