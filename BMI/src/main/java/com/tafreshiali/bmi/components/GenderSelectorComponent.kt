package com.tafreshiali.bmi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tafreshiali.bmi.R
import com.tafreshiali.bmi.ui.BmiPrimary
import com.tafreshiali.bmi.ui.BmiSecondary

@ExperimentalMaterial3Api
@Composable
fun GenderSelectorScreen() {
    val interactionSource = MutableInteractionSource()
    var genderList by remember {
        mutableStateOf(
            listOf(
                GenderItem(
                    id = 0,
                    genderTitle = "Male",
                    genderIcon = R.drawable.ic_male,
                    isSelected = true
                ),
                GenderItem(
                    id = 1,
                    genderTitle = "Female",
                    genderIcon = R.drawable.ic_female,
                ),
                GenderItem(
                    id = 2,
                    genderTitle = "Transgender",
                    genderIcon = R.drawable.ic_transgender,
                )
            )
        )
    }

    BmiScreenContainer(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BmiPrimary),
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Gender",
                    style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                ) {

                    items(items = genderList, key = { genderItem -> genderItem.id }) { genderItem ->
                        GenderItem(
                            modifier = Modifier
                                .size(125.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    genderList = genderList.map { listItem ->
                                        listItem.copy(isSelected = genderItem.id == listItem.id)
                                    }
                                },
                            genderTitle = genderItem.genderTitle,
                            genderIcon = genderItem.genderIcon,
                            isSelected = genderItem.isSelected,
                            genderTitleStyle = MaterialTheme.typography.labelLarge.copy(color = if (genderItem.isSelected) BmiPrimary else BmiSecondary),
                        )
                    }
                }

                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BmiSecondary)
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
                        text = "Next",
                        style = MaterialTheme.typography.bodyLarge.copy(color = BmiPrimary)
                    )
                }
            }
        },
        bottomBar = {
        })
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun GenderSelectionScreenPreview() {
    GenderSelectorScreen()
}