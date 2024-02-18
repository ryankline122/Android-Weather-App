package com.example.weatherapp.weatherservice

import com.example.weatherapp.api.Client
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import mu.KotlinLogging

class CurrentTemperatureService {
    fun getCurrentTempFor(location: String, callback: (Int) -> Unit) {
        Client().getCurrentWeatherFor(location) { result ->
            val temp = processResponse(response = result)
            if (temp != null) {
                callback(temp)
            }
        }
    }

    private fun processResponse(response: String): Int? {
        val logger = KotlinLogging.logger() {}
        logger.debug { response }

        return try {
            val json = Json.parseToJsonElement(response)
            val current = json.jsonObject["current"]
            val tempF = current?.jsonObject?.get("temp_f").toString().toDoubleOrNull()
            tempF?.toInt()

        } catch (e: Exception) {
            logger.error { e.toString() }
            null
        }
    }
}