package com.mphasis.domain.viewmodel

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.SavedStateHandle
import com.mphasis.domain.usecase.MphTestWeatherDataRepository
import com.mphasis.domain.usecase.MphWeatherByCityUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.Locale


/**
 * A view model test .
 *
 * @author Kal Tadesse
 */
@RunWith(MockitoJUnitRunner::class)
class MphViewModelTest {

    @Mock
    lateinit var context: Context

    private lateinit var viewModel: MphWeatherByCityViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = MphWeatherByCityViewModel(
            useCase = MphWeatherByCityUseCaseImpl(MphTestWeatherDataRepository()),
            savedStateHandle = SavedStateHandle(),
            geocoder = Geocoder(context, Locale("en"))
        )
        viewModel.weatherByCity(0.0, 0.0)
    }

    @Test
    fun test() = runTest {
        assertEquals(viewModel.cityWeatherState.value.name, "New York")
    }
}

class MainDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
