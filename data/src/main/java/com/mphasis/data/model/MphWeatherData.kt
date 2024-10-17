package com.mphasis.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A weather data file.
 * This will be used in parsing and
 * serialization.
 *
 * @author Kal Tadesse
 */
@Serializable
data class WeatherData(
    val coord: Coordinates = Coordinates(),
    val weather: List<Weather> = emptyList(),
    val base: String = "",
    val main: Main = Main(),
    val visibility: Int = 0,
    val wind: Wind = Wind(),
    val clouds: Clouds = Clouds(),
    val dt: Long = 0L,
    val sys: Sys = Sys(),
    @SerialName("timezone") val timeZone: Int = 0,
    val id: Long = 0L,
    val name: String = "",
    val cod: Int = 0
)

@Serializable
data class Coordinates(
    val lon: Float = 0.0f,
    val lat: Float = 0.0f
)

@Serializable
data class Weather(
    val id: Int = 0,
    val main: String = "",
    val description: String = "",
    val icon: String = ""
)

@Serializable
data class Main(
    val temp: Float = 0.0f,
    @SerialName("feels_like")
    val feelsLike: Float = 0.0f,
    @SerialName("temp_min")
    val tempMin: Float = 0.0f,
    @SerialName("temp_max")
    val tempMax: Float = 0.0f,
    val pressure: Int = 0,
    val humidity: Int = 0,
    @SerialName("sea_level")
    val seaLevel: Int = 0,
    @SerialName("grnd_level")
    val grndLevel: Int = 0
)

@Serializable
data class Wind(val speed: Float = 0.0f, val deg: Float = 0.0f)

@Serializable
data class Clouds(val all: Int = 0)

@Serializable
data class Sys(
    val type: Int = 0,
    val id: Int = 0,
    val country: String = "",
    val sunrise: Long = 0L,
    val sunset: Long = 0L
)





