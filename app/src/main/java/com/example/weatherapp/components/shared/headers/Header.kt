package com.example.weatherapp.components.shared.headers

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Header {
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
}