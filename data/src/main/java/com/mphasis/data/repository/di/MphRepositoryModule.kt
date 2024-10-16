package com.mphasis.data.repository.di

import com.mphasis.data.repository.MphWeatherByCityRepository
import com.mphasis.data.repository.MphWeatherByCityRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * This will be used to inject repositories
 * dependencies.
 *
 * @author Kal Tadesse
 */


/**
 * A module that will be used to inject
 * repositories to serve weather by city.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class MphWeatherByCityUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun providesWeatherByCityUseCase(repository: MphWeatherByCityRepositoryImpl):
            MphWeatherByCityRepository

}