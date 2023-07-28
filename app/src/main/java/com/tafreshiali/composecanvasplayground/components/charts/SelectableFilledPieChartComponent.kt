package com.tafreshiali.composecanvasplayground.components.charts

import android.graphics.Color.GRAY
import android.graphics.Color.toArgb
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tafreshiali.composecanvasplayground.ui.theme.White
import com.tafreshiali.composecanvasplayground.utils.Constance.COMPLETE_CIRCLE_DEGREE
import com.tafreshiali.composecanvasplayground.utils.calculateVerticalCenterOfAText

@ExperimentalTextApi
@Composable
fun SelectableFilledPieChartComponent(
    modifier: Modifier = Modifier,
    pieInformationList: List<PieItem>
) {
    val titleTextMeasurer = rememberTextMeasurer()

    val pieItemList by remember {
        mutableStateOf(pieInformationList)
    }

    val totalPieItemsPercentageValue = remember {
        mutableStateOf(pieInformationList.sumOf { pieItem ->
            pieItem.percentage
        })
    }

    val anglePerValue = remember {
        mutableStateOf(COMPLETE_CIRCLE_DEGREE / totalPieItemsPercentageValue.value)
    }

    var currentStartAngle by remember {
        mutableStateOf(0f)
    }


    BoxWithConstraints(modifier = modifier.drawWithCache {

        val pieTitleCircleRadius = size.minDimension / 5.5f

        val pieTitleOuterCircleRadius = size.minDimension / 4.8f

        //actually we wanna bound our pie title to the diameter of the innerCircle
        val pieTitleConstraintSize = pieTitleCircleRadius.toInt() * 2

        val pieChartItemsRadius = size.minDimension / 3f
        val pieChartItemsDiameter = pieChartItemsRadius * 2


        val measuredPieTitle = titleTextMeasurer.measure(
            AnnotatedString("Monthly Expenses"),
            constraints = Constraints.fixed(
                width = pieTitleConstraintSize,
                height = pieTitleConstraintSize
            ),
            style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Center)
        )

        onDrawBehind {
            pieItemList.forEach { pieItem ->
                val angleToDraw = pieItem.percentage * anglePerValue.value
                pieItem(
                    pieItem = pieItem,
                    startAngle = currentStartAngle,
                    sweepAngle = -angleToDraw,
                    arcSize = Size(width = pieChartItemsDiameter, height = pieChartItemsDiameter)
                )
                currentStartAngle -= angleToDraw
            }

            pieChartTitleContainer(
                pieTitleCircleRadius = pieTitleCircleRadius,
                pieTitle = measuredPieTitle,
                pieTitleOuterCircleRadius = pieTitleOuterCircleRadius
            )
        }
    }, content = {})
}

private fun DrawScope.pieItem(
    pieItem: PieItem,
    startAngle: Float,
    sweepAngle: Float,
    arcSize: Size
) {
    drawArc(
        color = pieItem.color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        topLeft = calculateTheCenterOffsetForTheThetaArc(arcSize),
        size = arcSize,
        useCenter = true
    )
}

private fun DrawScope.calculateTheCenterOffsetForTheThetaArc(arcSize: Size): Offset =
    Offset(
        x = (size.width - arcSize.width) / 2f,
        y = (size.height - arcSize.height) / 2f
    )


@ExperimentalTextApi
private fun DrawScope.pieChartTitleContainer(
    pieTitleCircleRadius: Float,
    pieTitle: TextLayoutResult,
    pieTitleOuterCircleRadius: Float
) {
    //Outer Circle
    drawContext.canvas.nativeCanvas.apply {
        drawCircle(
            center.x,
            center.y,
            pieTitleOuterCircleRadius,
            android.graphics.Paint().apply {
                color = White.copy(alpha = 0.6f).toArgb()
                setShadowLayer(30f, 0f, 0f, GRAY)
            }
        )
    }

    //InnerCircle
    drawCircle(color = Color.LightGray, radius = pieTitleCircleRadius)

    //Title
    drawText(
        textLayoutResult = pieTitle,
        topLeft = Offset(
            x = center.x - pieTitle.size.width / 2,
            y = center.y - pieTitle.calculateVerticalCenterOfAText(),
        ),
        color = Color.Black,

        )
}

@OptIn(ExperimentalTextApi::class)
@Preview
@Composable
fun SelectableFilledPieChartComponentPreview() {
    SelectableFilledPieChartComponent(
        modifier = Modifier.size(350.dp),
        pieInformationList = pieChartInformation()
    )
}