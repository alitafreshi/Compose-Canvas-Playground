package com.tafreshiali.bmi.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tafreshiali.bmi.R
import com.tafreshiali.bmi.ui.BmiPrimary
import com.tafreshiali.bmi.ui.BmiSecondary
import com.tafreshiali.bmi.ui.BmiTertiary

@ExperimentalMaterial3Api
@Composable
fun BmiScreenContainer(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
    bottomBar: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_menu),
                            contentDescription = "menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_action),
                            contentDescription = "action"
                        )
                    }
                },
                title = {}
            )

        },
        bottomBar = bottomBar
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
fun GenderItem(
    modifier: Modifier = Modifier,
    genderTitle: String,
    genderTitleStyle: TextStyle,
    genderIcon: Int,
    isSelected: Boolean = false
) {
    val selectedGenderAnimatedColor by animateColorAsState(
        if (isSelected) BmiSecondary else BmiTertiary,
        label = "selectedGenderColor",
        animationSpec = tween(delayMillis = 5)
    )

    val selectedGenderAnimatedGenderColor by animateColorAsState(
        if (isSelected) BmiPrimary else BmiSecondary,
        label = "selectedGenderIconColor",
        animationSpec = tween(delayMillis = 5)
    )

    Box(
        modifier = modifier.background(
            color = selectedGenderAnimatedColor,
            shape = RoundedCornerShape(size = 45.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = genderIcon),
                contentDescription = "genderIcon",
                tint = selectedGenderAnimatedGenderColor
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            Text(text = genderTitle, style = genderTitleStyle)
        }
    }
}

@Stable
data class GenderItem(
    val id: Int,
    val genderTitle: String,
    val genderIcon: Int,
    val isSelected: Boolean = false
)

@Preview(showBackground = true, backgroundColor = 0x19222a)
@Composable
fun GenderItemPreview() {
    GenderItem(
        modifier = Modifier.size(100.dp),
        genderTitle = "male",
        genderIcon = R.drawable.ic_male,
        genderTitleStyle = MaterialTheme.typography.titleMedium
    )
}