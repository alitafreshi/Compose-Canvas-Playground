package com.tafreshiali.composecanvasplayground.presentation

import android.os.Bundle
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
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.tafreshiali.composecanvasplayground.components.charts.SelectableFilledPieChartComponent
import com.tafreshiali.composecanvasplayground.components.charts.pieChartInformation

@ExperimentalTextApi
class SelectableFilledPieChartComponentFragment : Fragment() {

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
                SelectableFilledPieChartComponent(
                    modifier = Modifier.size(450.dp),
                    pieInformationList = pieChartInformation()
                )
            }
        }
    }
}