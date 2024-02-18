package com.example.weatherapp.api

import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class Client {
    private val httpClient = OkHttpClient()
    private val apiKey = "60a020e32e5a46ea901164338241702"
    private val baseURL = "https://api.weatherapi.com/v1"


    fun getCurrentWeatherFor(location: String, callback: (String) -> Unit) {
        val queryUrlBuilder: HttpUrl.Builder = "$baseURL/current.json".toHttpUrl().newBuilder()
        queryUrlBuilder.addQueryParameter("key", apiKey)
        queryUrlBuilder.addQueryParameter("q", location)

        val request: Request = Request.Builder()
            .url(queryUrlBuilder.build())
            .build()

        httpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback("failure")
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body?.string() ?: "responseError"
                println(result)
                callback(result)
            }
        })
    }
}