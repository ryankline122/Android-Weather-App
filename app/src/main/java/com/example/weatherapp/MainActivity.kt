package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.weatherapp.components.bottomnav.BottomNavigation
import com.example.weatherapp.components.shared.headers.Header
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.weatherservice.CurrentTemperatureService

class MainActivity : ComponentActivity() {
    private var currentTemp by mutableIntStateOf(0)
    private var location by mutableStateOf("49401")
    private val weatherSvc = CurrentTemperatureService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Header().LocationHeader(location = "Allendale, MI")
                        Header().CurrentTemp(temperature = currentTemp)
                        BottomNavigation().BottomNavigationComposable(items = BottomNavigation().createBottomNavigationItems())
                    }
                }
            }
        }

        weatherSvc.getCurrentTempFor(location) { temp ->
            currentTemp = temp
        }
    }
}
