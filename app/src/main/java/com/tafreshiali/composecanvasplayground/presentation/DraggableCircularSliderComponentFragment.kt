package com.tafreshiali.composecanvasplayground.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.tafreshiali.composecanvasplayground.components.DraggableCircularSliderComponent
import com.tafreshiali.composecanvasplayground.ui.theme.Pink40
import com.tafreshiali.composecanvasplayground.ui.theme.Purple40

class DraggableCircularSliderComponentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(context = requireContext()).apply {
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                DraggableCircularSliderComponent(
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
                )
            }
        }
    }
}