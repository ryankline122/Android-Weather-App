package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.example.weatherapp.api.Client
import com.example.weatherapp.bottomnav.BottomNavigation
import com.example.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.json.JSONObject

class MainActivity : ComponentActivity() {
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
                        CurrentTemp(temperature = getCurrentTempFor("49401"))
                        BottomNavigation().BottomNavigationComposable(items = BottomNavigation().createBottomNavigationItems())
                    }
                }
            }
        }
    }
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

fun getCurrentTempFor(location: String): Int {
    Client().getCurrentWeatherFor(location) { result ->
        println(result)
//        val map = Json.decodeFromString<Map<String, Any>>(result)
//        val current = map["current"] as? Map<*, *>
//        val tempF = current?.get("temp_f")
    }
    return 0
}



