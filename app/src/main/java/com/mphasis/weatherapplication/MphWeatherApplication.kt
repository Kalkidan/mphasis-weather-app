package com.mphasis.weatherapplication

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MphWeatherApplication : Application(), ImageLoaderFactory {

    @Inject
    lateinit var imageLoader: dagger.Lazy<ImageLoader>

    override fun newImageLoader(): ImageLoader = imageLoader.get()
}