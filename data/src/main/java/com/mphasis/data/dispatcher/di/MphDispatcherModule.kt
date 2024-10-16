package com.mphasis.data.dispatcher.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

/**
 * This will be used as a module in order to
 * provide different instances and types
 * of dispatchers for coroutine functionality.
 *
 * @author Kal Tadesse
 */
@Module
@InstallIn(SingletonComponent::class)
class MphDispatcherModule {

    @Provides
    @Dispatcher(MphDispatchers.IO)
    fun providesIODispatcher() = Dispatchers.IO

    @Provides
    @Dispatcher(MphDispatchers.Default)
    fun providesDefaultDispatcher() = Dispatchers.Default

}

/**
 * A predefined qualifier for identifying the correct
 * type of dispatcher during dependency injection.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Dispatcher(val dispatchers: MphDispatchers)

/**
 * Types of coroutine dispatchers within the application.
 */
enum class MphDispatchers {
    Default,
    IO
}