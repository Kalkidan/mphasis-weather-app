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
    val coord: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    @SerialName("timezone") val timeZone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)

@Serializable
data class Coordinates(
    val lon: Float,
    val lat: Float
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Main(
    val temp: Float,
    @SerialName("feels_like")
    val feelsLike: Float,
    @SerialName("temp_min")
    val tempMin: Float,
    @SerialName("temp_max")
    val tempMax: Float,
    val pressure: Int,
    val humidity: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("grnd_level")
    val grndLevel: Int
)

@Serializable
data class Wind(val speed: Float, val deg: Float)

@Serializable
data class Clouds(val all: Int)

@Serializable
data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)





