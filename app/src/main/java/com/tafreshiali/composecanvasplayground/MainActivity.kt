package com.tafreshiali.composecanvasplayground

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tafreshiali.composecanvasplayground.components.BasicDraggableComponent
import com.tafreshiali.composecanvasplayground.components.DraggableCircularSliderComponent
import com.tafreshiali.composecanvasplayground.components.charts.PieItem
import com.tafreshiali.composecanvasplayground.components.charts.SelectableFilledPieChartComponent
import com.tafreshiali.composecanvasplayground.components.charts.pieChartInformation
import com.tafreshiali.composecanvasplayground.ui.theme.ComposeCanvasPlaygroundTheme
import com.tafreshiali.composecanvasplayground.ui.theme.Pink40
import com.tafreshiali.composecanvasplayground.ui.theme.Purple40

@ExperimentalTextApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCanvasPlaygroundTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {

                    SelectableFilledPieChartComponent(
                        modifier = Modifier.size(350.dp),
                        pieInformationList =  pieChartInformation()
                    )

                    //BasicDraggableComponent()

                /*    DraggableCircularSliderComponent(
                        modifier = Modifier
                            .size(250.dp)
                            .background(Color.Black),
                        initialValue = 0,
                        primaryColor = Purple40,
                        secondaryColor = Pink40,
                        circleRadius = 230f,
                        onPositionChange = { position ->
                            Log.d("onPositionChange", "changedPosition : $position")
                        }
                    )*/
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