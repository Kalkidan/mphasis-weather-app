package com.mphasis.weatherapplication

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mphasis.domain.model.MphWeatherDataByCity
import com.mphasis.weatherapplication.ui.component.Home
import com.mphasis.weatherapplication.ui.theme.WeatherApplicationTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This will be Ui test for the application
 *
 * @author Kal Tadesse
 */
@RunWith(AndroidJUnit4::class)
class MphUiScreenTest {

    /*********************************************************
     * NOTE - when we test this - please make sure
     * that the screen is ON - otherwise it will
     * fail to pass.
     *
     * ******************************************************/

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            WeatherApplicationTheme {
                val data =  remember { mutableStateOf(MphWeatherDataByCity()) }
                Home(data) { }
            }
        }
    }

    @Test
    fun launchTheAppAndTestNodes() {
        //Starting the app here and checking the nodes...
        //This is a simple UI test to show how it can be done.
        //Please feel free to elaborate further on it.
        composeTestRule.onNodeWithText("Place :").assertIsDisplayed()
    }
}