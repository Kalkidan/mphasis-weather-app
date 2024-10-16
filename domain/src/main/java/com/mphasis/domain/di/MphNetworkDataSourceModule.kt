package com.mphasis.domain.di

import com.mphasis.data.network.helper.MphNetworkDataSource
import com.mphasis.data.network.helper.MphNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This will be used to inject network data source
 * dependency.
 *
 * @author Kal Tadesse
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MphNetworkDataSourceModule {

    @Binds
    @Singleton
    abstract fun providesMelaNetworkDataSource(networkDataSource: MphNetworkDataSourceImpl): MphNetworkDataSource
}