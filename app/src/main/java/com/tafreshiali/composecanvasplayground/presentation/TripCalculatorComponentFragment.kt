package com.tafreshiali.composecanvasplayground.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.tafreshiali.composecanvasplayground.R
import com.tafreshiali.composecanvasplayground.components.TripCalculatorComponent
import com.tafreshiali.composecanvasplayground.ui.theme.Green

@ExperimentalTextApi
class TripCalculatorComponentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(context = requireContext()).apply {
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                TripCalculatorScreen()
            }
        }
    }
}

@ExperimentalTextApi
@Composable
fun TripCalculatorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DaysComponent()

        InformationComponent(
            title = "On average you need to ride",
            description = "4 hours 10 minutes"
        )

        InformationComponent(
            title = "On some days, you will have to climb over",
            description = "255 meters"
        )

        InformationComponent(
            title = "The overall difficulty level is",
            description = "Easy"
        )

        TripCalculatorComponent(componentSize = 300.dp, numbers = 1..25)

        BottomContainerComponent()

    }
}

@Composable
private fun DaysComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "16 days",
            style = MaterialTheme.typography.displayLarge.copy(color = Green)
        )
        IconButton(
            onClick = {  },
            modifier = Modifier
                .size(40.dp)
                .background(color = Green, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )

        }

    }
}

@Composable
private fun InformationComponent(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {

    Column(modifier = modifier) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium.copy(color = Color.Black.copy(alpha = 0.5f))
        )

        Text(
            text = description,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun BottomItemComponent(modifier: Modifier = Modifier, itemName: String, icon: Int) {

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = modifier,
            painter = painterResource(id = icon),
            contentDescription = itemName
        )
        Text(text = itemName)
    }
}

@Composable
private fun BottomContainerComponent(modifier: Modifier = Modifier) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomItemComponent(itemName = "45h 452m", icon = R.drawable.ic_timer_24)

        BottomItemComponent(
            itemName = "960 km",
            icon = R.drawable.ic_distance_km_24,
            modifier = Modifier.rotate(90f)
        )

        BottomItemComponent(itemName = "9780 m", icon = R.drawable.ic_distance_added_meter_24)

        BottomItemComponent(itemName = "11470 m", icon = R.drawable.ic_south_distance_24)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFF)
@Composable
fun BottomContainerComponentPreview() {
    BottomContainerComponent()
}

@Preview(showBackground = true, backgroundColor = 0xFFFF)
@Composable
fun BottomItemComponentPreview() {
    BottomItemComponent(itemName = "45h 452m", icon = R.drawable.ic_timer_24)
}

@Preview(showBackground = true, backgroundColor = 0xFFFF)
@Composable
fun InformationComponentPreview() {
    InformationComponent(title = "skldfnsnfgknsfk", description = "asgnmfadafmnlkdfan")
}

@ExperimentalTextApi
@Preview(showBackground = true, backgroundColor = 0xFFFF)
@Composable
fun TripCalculatorScreenPreview() {
    TripCalculatorScreen()
}