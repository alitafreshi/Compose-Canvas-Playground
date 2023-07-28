package com.tafreshiali.composecanvasplayground.components.charts

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
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
import com.tafreshiali.composecanvasplayground.utils.calculateVerticalCenterOfAText

@ExperimentalTextApi
@Composable
fun SelectableFilledPieChartComponent(
    modifier: Modifier = Modifier,
    pieInformationList: List<PieItem>
) {
    val titleTextMeasurer = rememberTextMeasurer()

    BoxWithConstraints(modifier = modifier.drawWithCache {

        val pieTitleCircleRadius = size.minDimension / 5.5f

        val pieTitleOuterCircleRadius = size.minDimension / 4.8f

        //actually we wanna bound our pie title to the diameter of the innerCircle
        val pieTitleConstraintSize = pieTitleCircleRadius.toInt() * 2


        val measuredPieTitle = titleTextMeasurer.measure(
            AnnotatedString("Monthly Expenses"),
            constraints = Constraints.fixed(
                width = pieTitleConstraintSize,
                height = pieTitleConstraintSize
            ),
            style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Center)
        )

        onDrawBehind {
            pieChartTitleContainer(
                pieTitleCircleRadius = pieTitleCircleRadius,
                pieTitle = measuredPieTitle,
                pieTitleOuterCircleRadius = pieTitleOuterCircleRadius
            )
        }
    }, content = {})
}

@ExperimentalTextApi
private fun DrawScope.pieChartTitleContainer(
    pieTitleCircleRadius: Float,
    pieTitle: TextLayoutResult,
    pieTitleOuterCircleRadius: Float
) {

    //Outer Circle
    drawCircle(color = Color.LightGray, radius = pieTitleOuterCircleRadius, alpha = 0.3f)

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