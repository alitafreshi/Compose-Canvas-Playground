package com.tafreshiali.composecanvasplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.tafreshiali.composecanvasplayground.databinding.FragmentContainerLayoutBinding
import com.tafreshiali.composecanvasplayground.ui.theme.ComposeCanvasPlaygroundTheme

@ExperimentalTextApi
class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCanvasPlaygroundTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                ) {
                    AndroidViewBinding(
                        factory = FragmentContainerLayoutBinding::inflate,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeCanvasPlaygroundTheme {
        Greeting("Android")
    }
}