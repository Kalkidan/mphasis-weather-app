package com.mphasis.domain.model


/**
 * Weather data that will represent the current
 * state of the UI.
 *
 * @author Kal Tadesse
 */
class MphWeatherDataByCity(
    var name: String = "",
    var weatherDescription: String = "",
    var weatherType: String = "",
    var feelsLike: Float = 0.0f,
    var humidity: Int = 0,
    var temprature: Float = 0.0f,
    var pressure: Int = 0,
    var visibility: Int = 0,
    var icon: String = "10d"
) : MphCommonData

/**
 * A marker interface for common use.
 * Also applies for avoiding an implicit cast
 * to Any? at the network layer.
 *
 * @author Kal Tadesse
 */
interface MphCommonData

