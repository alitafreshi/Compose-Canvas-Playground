package com.tafreshiali.composecanvasplayground.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
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
import com.tafreshiali.composecanvasplayground.utils.calculateAnOffsetOnCircle
import com.tafreshiali.composecanvasplayground.utils.calculateAnglePerValue
import com.tafreshiali.composecanvasplayground.utils.calculateDistanceFrom
import com.tafreshiali.composecanvasplayground.utils.calculateHorizontalCenterOfAText
import com.tafreshiali.composecanvasplayground.utils.calculateTheCenterOffsetForTheThetaArc
import com.tafreshiali.composecanvasplayground.utils.calculateVerticalCenterOfAText
import com.tafreshiali.composecanvasplayground.utils.convertFromOffsetToDegrees
import com.tafreshiali.composecanvasplayground.utils.divideCircleAnglesInToParts
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlin.math.absoluteValue
import kotlin.math.min

@ExperimentalTextApi
@Composable
fun TripCalculatorComponent(
    numbers: IntRange,
    initAngle: Int = 180
) {

    val numberTextMeasurer = rememberTextMeasurer()

    val numbersList by remember { mutableStateOf(numbers) }

    val anglePerValue = remember {
        mutableStateOf(calculateAnglePerValue(divideCount = numbers.toList().size.toFloat()))
    }

    var canvasCenter by remember { mutableStateOf(Offset.Zero) }

    //Handling user Dragging

    var draggedDistance by remember { mutableStateOf(0f) }

    var dragStartedAngle by remember { mutableStateOf(0f) }

    var draggedAngle by remember { mutableStateOf(0f) }

    var oldDraggedAngle by remember { mutableStateOf(0f) }

    //animated Arc

    var shouldLaunchTheAnimations by remember {
        mutableStateOf(false)
    }

    val animatedStartAngle = remember {
        Animatable(-90f)
    }

    val animatedSweepAngle = remember {
        Animatable(0f)
    }

    BoxWithConstraints(modifier = Modifier
        .size(450.dp)
        .pointerInput(true) {
            detectDragGestures(
                onDragStart = { initialDraggedOffset: Offset ->
                    dragStartedAngle =
                        initialDraggedOffset
                            .convertFromOffsetToDegrees(centerOffset = canvasCenter)
                            .toFloat()
                    shouldLaunchTheAnimations = false
                },
                onDrag = { change, _ ->

                    val draggedOffset = change.position
                    // Calculate the distance between the dragged point and the center of the circle to make sure user dragged on correct circle
                    draggedDistance = draggedOffset.calculateDistanceFrom(offset = canvasCenter)

                    // Calculate the initial angle between the center of the circle and the dragged point
                    val newDraggedAngle =
                        draggedOffset
                            .convertFromOffsetToDegrees(centerOffset = canvasCenter)
                            .toFloat()

                    // Check if the dragged point is inside the circle
                    if (draggedDistance <= (min(
                            size.width.absoluteValue,
                            size.height.absoluteValue
                        ) / 2.5f)
                    ) {
                        // Calculate the changed angle during this drag event
                        val deltaAngle = newDraggedAngle - dragStartedAngle

                        // Calculate the changed angle during this drag event
                        draggedAngle = (oldDraggedAngle - deltaAngle + 360) % 360
                    }
                },
                onDragEnd = {

                    // Check if the dragged point is inside the circle
                    if (draggedDistance <= (min(
                            size.width.absoluteValue,
                            size.height.absoluteValue
                        ) / 2.5f)
                    ) {
                        // Update oldDraggedAngle for the next drag event
                        oldDraggedAngle = draggedAngle
                        shouldLaunchTheAnimations = true
                        Log.d(
                            "ANIMATED_ANGELS",
                            "shouldLaunchTheAnimations is $shouldLaunchTheAnimations"
                        )

                    }
                })
        }
        .drawWithCache {
            canvasCenter = size.center
            val outerCircleRadius = size.minDimension / 2.5f
            val imaginaryInnerCircleRadius = outerCircleRadius / 2.7f
            val numbersCircleRadius = outerCircleRadius / 1.16f
            val animatedCircleDiameter =
                ((numbersCircleRadius - imaginaryInnerCircleRadius) / 2f) + canvasCenter.x


            onDrawBehind {
                staticComponents(
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

                    draggableNumbersCircle(
                        number = number,
                        numberTitle = measuredNumber,
                        numbersCount = numbersList.toList().size.toFloat(),
                        numbersCircleRadius = numbersCircleRadius,
                        startAngle = initAngle,
                        anglePerValue = anglePerValue.value,
                        draggedAngleInDegree = draggedAngle
                    )

                    animatedCircular(
                        animatedCircleSize = Size(
                            width = animatedCircleDiameter,
                            height = animatedCircleDiameter
                        ),
                        startAngle = animatedStartAngle.value,
                        sweepAngle = animatedSweepAngle.value
                    )
                }
            }
        }, contentAlignment = Alignment.Center, content = {

        LaunchedEffect(key1 = shouldLaunchTheAnimations) {
            if (shouldLaunchTheAnimations) {
                val anim1Result = async {
                    animatedStartAngle.animateTo(targetValue = 270f,
                        animationSpec = repeatable(
                            iterations = 2,
                            animation = keyframes {
                                durationMillis = 2000
                                -90f at 0
                                0f at 500
                                90f at 1000
                                180f at 1500
                                270f at 2000
                            }
                        )
                    )
                }

                val anim2Result = async {
                    animatedSweepAngle.animateTo(
                        targetValue = 0f,
                        animationSpec = repeatable(
                            iterations = 2,
                            animation = keyframes {
                                durationMillis = 2000
                                0.0f at 0
                                30f at 150
                                50f at 250
                                90f at 500
                                0f at 2000
                            },
                        )
                    )
                }
                awaitAll(anim1Result, anim2Result)
            }
        }
    })
}

private fun DrawScope.staticComponents(
    outerCircleRadius: Float,
    imaginaryInnerCircleRadius: Float,
) {

    //Center Point
    drawCircle(color = Color.Black, radius = outerCircleRadius / 25f)

    //Outer Circle
    drawCircle(color = Color.Black, radius = outerCircleRadius, style = Stroke(width = 5f))

    //Pointer Circle
    drawCircle(
        color = Color.Green,
        radius = outerCircleRadius / 15f,
        center = Offset(
            x = center.x - (outerCircleRadius - imaginaryInnerCircleRadius),
            y = center.y
        )
    )

    drawCircle(
        color = Color.Black,
        radius = outerCircleRadius / 15f,
        center = Offset(
            x = center.x - (outerCircleRadius - imaginaryInnerCircleRadius),
            y = center.y
        ),
        style = Stroke(width = 3f)
    )
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.draggableNumbersCircle(
    number: Int,
    numberTitle: TextLayoutResult,
    numbersCount: Float,
    numbersCircleRadius: Float,
    startAngle: Int,
    anglePerValue: Float,
    draggedAngleInDegree: Float
) {
    val angleToDrawInDegree = divideCircleAnglesInToParts(
        index = number,
        divideCount = numbersCount
    ) - anglePerValue


    val pointsOffsetOnNumbersCircle = center.calculateAnOffsetOnCircle(
        radius = numbersCircleRadius,
        thetaDegrees = ((if (number == 1) 0 else angleToDrawInDegree).toInt()) + startAngle
    )

    rotate(degrees = draggedAngleInDegree) {
        rotate(
            degrees = angleToDrawInDegree, pivot = Offset(
                x = pointsOffsetOnNumbersCircle.x,
                y = pointsOffsetOnNumbersCircle.y
            )
        ) {
            drawText(
                textLayoutResult = numberTitle,
                color = Color.Black,
                topLeft = Offset(
                    x = pointsOffsetOnNumbersCircle.x - numberTitle.calculateHorizontalCenterOfAText(),
                    y = pointsOffsetOnNumbersCircle.y - numberTitle.calculateVerticalCenterOfAText()
                )
            )
        }
    }
}

private fun DrawScope.animatedCircular(
    animatedCircleSize: Size,
    startAngle: Float,
    sweepAngle: Float
) {
    Log.d("ANIMATED_ANGELS", "startAngle is $startAngle and the sweepAngle is $sweepAngle")
    drawArc(
        color = Color.Black,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = calculateTheCenterOffsetForTheThetaArc(animatedCircleSize),
        size = animatedCircleSize,
        style = Stroke(width = 5f, cap = StrokeCap.Round)
    )
}


@ExperimentalTextApi
@Preview(showBackground = true, backgroundColor = 0xFFFF)
@Composable
fun TripCalculatorComponentPreview() {
    TripCalculatorComponent(numbers = 1..25)
}