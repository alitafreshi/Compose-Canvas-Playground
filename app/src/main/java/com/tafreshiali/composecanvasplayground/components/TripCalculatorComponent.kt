package com.tafreshiali.composecanvasplayground.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tafreshiali.composecanvasplayground.utils.calculateAnOffsetOnCircle
import com.tafreshiali.composecanvasplayground.utils.calculateAnglePerValue
import com.tafreshiali.composecanvasplayground.utils.calculateHorizontalCenterOfAText
import com.tafreshiali.composecanvasplayground.utils.calculateVerticalCenterOfAText
import com.tafreshiali.composecanvasplayground.utils.divideCircleAnglesInToParts

@ExperimentalTextApi
@Composable
fun TripCalculatorComponent(numbers: IntRange, startAngle: Int = 180) {

    val numberTextMeasurer = rememberTextMeasurer()

    val numbersList by remember {
        mutableStateOf(numbers)
    }


    val anglePerValue = remember {
        mutableStateOf(calculateAnglePerValue(divideCount = numbers.toList().size.toFloat()))
    }

    BoxWithConstraints(modifier = Modifier
        .size(450.dp)
        .pointerInput(true) {
            detectDragGestures(
                onDragStart = { offset: Offset ->

                },
                onDrag = { change, _ ->

                },
                onDragEnd = {

                })
        }
        .drawWithCache {
            val outerCircleRadius = size.minDimension / 2.5f
            val imaginaryInnerCircleRadius = size.minDimension / 6f
            val numbersCircleRadius = size.minDimension / 3f

            onDrawBehind {
                draggableCircle(
                    outerCircleRadius = outerCircleRadius,
                    imaginaryInnerCircleRadius = imaginaryInnerCircleRadius
                )

                numbersList.forEach { number ->

                    val measuredNumber = numberTextMeasurer.measure(
                        AnnotatedString(number.toString()),
                        constraints = Constraints.fixed(
                            width = 100,
                            height = 50
                        ),
                        style = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Center)
                    )

                    val angleToDraw = divideCircleAnglesInToParts(
                        index = number,
                        divideCount = numbersList.toList().size.toFloat()
                    ) - anglePerValue.value


                    val pointsOffsetOnNumbersCircle = center.calculateAnOffsetOnCircle(
                        radius = numbersCircleRadius,
                        thetaDegrees = ((if (number == 1) 0 else angleToDraw).toInt()) + startAngle
                    )

                    rotate(
                        degrees = angleToDraw, pivot = Offset(
                            x =  pointsOffsetOnNumbersCircle.x,
                            y = pointsOffsetOnNumbersCircle.y
                        )
                    ) {

                        drawText(
                            textLayoutResult = measuredNumber,
                            color = Color.Black,
                            topLeft = Offset(
                                x = pointsOffsetOnNumbersCircle.x - measuredNumber.calculateHorizontalCenterOfAText(),
                                y = pointsOffsetOnNumbersCircle.y - measuredNumber.calculateVerticalCenterOfAText()
                            )
                        )
                    }

                }
            }
        }, contentAlignment = Alignment.Center, content = {})
}

private fun DrawScope.draggableCircle(outerCircleRadius: Float, imaginaryInnerCircleRadius: Float) {

    //Center Point
    drawCircle(color = Color.Black, radius = 15f)

    //Outer Circle
    drawCircle(color = Color.Black, radius = outerCircleRadius, style = Stroke(width = 5f))

    //
    drawCircle(
        color = Color.Green,
        radius = 20f,
        center = Offset(
            x = center.x - (outerCircleRadius - imaginaryInnerCircleRadius),
            y = center.y
        )
    )
}

@ExperimentalTextApi
@Preview
@Composable
fun TripCalculatorComponentPreview() {
    TripCalculatorComponent(numbers = 1..25)
}