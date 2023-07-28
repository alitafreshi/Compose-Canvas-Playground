package com.tafreshiali.composecanvasplayground.components.charts

import androidx.compose.ui.graphics.Color

data class PieItem(
    val title: String,
    val percentage: Int,
    val color: Color
)

fun pieChartInformation(): List<PieItem> = buildList {
    add(PieItem(title = "Go Shopping", percentage = 50, color = Color.Red))
    add(PieItem(title = "Go To The Cinema", percentage = 10, color = Color.Yellow))
    add(PieItem(title = "By A Car", percentage = 20, color = Color.Green))
    add(PieItem(title = "Vacation", percentage = 10, color = Color.Blue))
    add(PieItem(title = "Coffee", percentage = 10, color = Color.Cyan))
}