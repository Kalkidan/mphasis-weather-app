package com.mphasis.weatherapplication.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mphasis.domain.model.MphWeatherDataByCity
import com.mphasis.weatherapplication.BuildConfig
import com.mphasis.weatherapplication.R
import com.mphasis.weatherapplication.ui.theme.WeatherApplicationTheme

/**
 * This composable function will serve as a detailed
 * weather screen.
 */
@Composable
fun DetailWeatherScreen(cityWeatherState: State<MphWeatherDataByCity>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            //We can drive all these composables with a single state
            //Meaning, if the data changes for the state at one place,
            //the composables have to refresh - since location based
            //weather services are frequently updated so we need to make sure
            //we redraw accordingly
            CityDetails(cityWeatherState)
            WeatherIcon(cityWeatherState)
            WeatherDetail(cityWeatherState)
        }
    }
}

@Composable
fun CityDetails(cityWeatherState: State<MphWeatherDataByCity>) {
    Row {
        Text(
            text = "Place :",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Left,
            color = Color.White
        )

        Text(
            text = cityWeatherState.value.name,
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun WeatherIcon(cityWeatherState: State<MphWeatherDataByCity>) {
    val imageLoader = rememberAsyncImagePainter(
        model = "${BuildConfig.IMAGE_URL}/img/wn/${cityWeatherState.value.icon}@2x.png",
        onState = {

        }
    )
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Image(
            contentScale = ContentScale.Crop,
            painter = imageLoader,
            contentDescription = stringResource(id = R.string.weather),
        )
    }
}

@Composable
fun WeatherDetail(cityWeatherState: State<MphWeatherDataByCity>) {
    Column {
        Row {
            Text(
                text = "Type:",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Left,
                color = Color.White
            )

            Text(
                text = cityWeatherState.value.weatherType,
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
        Row {
            Text(
                text = "Feels Like:",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Left,
                color = Color.White
            )

            Text(
                text = cityWeatherState.value.feelsLike.toString(),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
        Row {
            Text(
                text = "Humidity:",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Left,
                color = Color.White
            )

            Text(
                text = cityWeatherState.value.humidity.toString(),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
        Row {
            Text(
                text = "Temprature:",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Left,
                color = Color.White
            )

            Text(
                text = cityWeatherState.value.temprature.toString(),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
        Row {
            Text(
                text = "Pressure:",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Left,
                color = Color.White
            )

            Text(
                text = cityWeatherState.value.pressure.toString(),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
        Row {
            Text(
                text = "Description of Weather:",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Left,
                color = Color.White
            )

            Text(
                text = cityWeatherState.value.weatherDescription,
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
        Row {
            Text(
                text = "Visibility:",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Left,
                color = Color.White
            )

            Text(
                text = cityWeatherState.value.visibility.toString(),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherApplicationTheme {
        DetailWeatherScreen(mutableStateOf(MphWeatherDataByCity()))
    }
}