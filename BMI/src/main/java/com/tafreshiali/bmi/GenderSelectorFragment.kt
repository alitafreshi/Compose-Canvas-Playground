package com.tafreshiali.bmi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.tafreshiali.bmi.components.GenderSelectorScreen

@ExperimentalMaterial3Api
class GenderSelectorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(context = requireContext()).apply {
        setContent {
            GenderSelectorScreen()
        }
    }
}