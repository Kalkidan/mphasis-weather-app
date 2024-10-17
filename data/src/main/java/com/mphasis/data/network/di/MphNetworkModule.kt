package com.mphasis.data.network.di

import android.content.Context
import android.location.Geocoder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mphasis.data.BuildConfig
import com.mphasis.data.network.MphRetrofitApi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.Locale
import javax.inject.Singleton

/**
 * A network module DI file - this will carry
 * all the dependencies we need in order to make
 * a network call to get weather data.
 *
 * @author Kal Tadesse
 */
@Module
@InstallIn(SingletonComponent::class)
class MphNetworkModule {

    /*************************************************
     * Note - all the dependencies we need in
     * this file are going to be all singleton
     * instances - since the network components
     * are used through out the app, it benefits
     * to maintain them on a singleton basis.
     * **********************************************/

    /**
     * Provides a BASE url for the network functionality.
     */
    @Provides
    @Singleton
    fun providesBaseUrl() = BuildConfig.BASE_URL

    /**
     * Provides JSON for serialization
     */
    @Provides
    @Singleton
    fun providesNetworkJson() : Json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Provides OkHttp call factory.
     */
    @Provides
    @Singleton
    fun okHttpCallFactory() : Call.Factory {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        ).build()
    }

    /**
     * Provides a retrofit instance with appropriate
     * settings.
     */
    @Provides
    @Singleton
    fun providesRetrofitInstance(
        baseUrl: String,
        networkJson: Json,
        okhttpCallFactory: Lazy<Call.Factory>
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            ).build()
    }

    /**
     * Provides a retrofit network interface.
     */
    @Provides
    @Singleton
    fun providesMelaRetrofitApi(api: Retrofit): MphRetrofitApi {
        return api.create(MphRetrofitApi::class.java)
    }

    /**
     * Provides Locale for usage in [Geocoder].
     */
    @Provides
    @Singleton
    fun providesLocalForGeoCoder() = Locale("en")

    /**
     * Provides an instance of [Geocoder].
     */
    @Provides
    @Singleton
    fun providesGeocoderInstance(@ApplicationContext context: Context, locale: Locale) =
        Geocoder(context, locale)


}