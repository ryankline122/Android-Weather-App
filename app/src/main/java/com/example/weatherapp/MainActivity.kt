package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.api.Client
import com.example.weatherapp.bottomnav.BottomNavigation
import com.example.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import mu.KotlinLogging

class MainActivity : ComponentActivity() {
    private var currentTemp by mutableIntStateOf(0)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        LocationHeader(location = "Allendale, MI")
                        CurrentTemp(temperature = currentTemp)
                        BottomNavigation().BottomNavigationComposable(items = BottomNavigation().createBottomNavigationItems())
                    }
                }
            }
        }

        getCurrentTempFor("49401")
    }

    @Composable
    fun LocationHeader(location: String, modifier: Modifier = Modifier) {
        Surface() {
            Text(
                text = location,
                fontSize = 24.sp,
                modifier = modifier
                    .padding(24.dp)
            )
        }
    }

    @Composable
    fun CurrentTemp(temperature: Int, modifier: Modifier = Modifier) {
        Surface {
            Text(
                text = "$temperature° F",
                fontSize = 34.sp,
                modifier = modifier
                    .padding(18.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,

                )
        }
    }

    private fun getCurrentTempFor(location: String) {
        Client().getCurrentWeatherFor(location) { result ->
            processResponse(response = result)
        }
    }

    private fun processResponse(response: String) {
        val logger = KotlinLogging.logger() {}
        logger.debug { response }

        try {
            val json = Json.parseToJsonElement(response)
            logger.debug { json }

            val current = json.jsonObject["current"]
            logger.debug { current}

            val tempF = current?.jsonObject?.get("temp_f").toString().toDoubleOrNull()
            if (tempF != null) {
                currentTemp = tempF.toInt()
            }

        } catch (e: Exception) {
            logger.error { e.toString() }
            currentTemp = -1
        }
    }
}
