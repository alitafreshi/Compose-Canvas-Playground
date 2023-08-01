package com.tafreshiali.composecanvasplayground.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(context = requireContext()).apply {
        setContent {
            HomeScreen(onComponentClicked = { component ->
                findNavController().navigate(component.direction)
            })
        }
    }
}


@Composable
fun HomeScreen(onComponentClicked: (Component) -> Unit) {
    val componentsList by remember {
        mutableStateOf(
            listOf(
                Component(
                    id = 0,
                    name = "BasicDraggableComponent",
                    direction = HomeFragmentDirections.actionHomeFragmentToBasicDraggableComponentFragment()
                ),
                Component(
                    id = 1,
                    name = "DraggableCircularSliderComponent",
                    direction = HomeFragmentDirections.actionHomeFragmentToDraggableCircularSliderComponentFragment()
                ),
                Component(
                    id = 2,
                    name = "SelectableFilledPieChartComponent",
                    direction = HomeFragmentDirections.actionHomeFragmentToSelectableFilledPieChartComponentFragment()
                )
            )
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(20.dp)
    ) {
        items(items = componentsList, key = { component -> component.id }) { component ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable(onClick = { onComponentClicked(component) })
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = component.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = component.name
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen() {}
}

data class Component(val id: Int, val name: String, val direction: NavDirections)