package com.mphasis.domain.usecase.di

import com.mphasis.domain.usecase.MphWeatherByCityUseCase
import com.mphasis.domain.usecase.MphWeatherByCityUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * This will be used to inject use case
 * dependencies.
 *
 * @author Kal Tadesse
 */


/**
 * A module that will be used to inject
 * use cases to serve weather by city.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class MphWeatherByCityUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun providesWeatherByCityUseCase(useCase: MphWeatherByCityUseCaseImpl) :
            MphWeatherByCityUseCase

}