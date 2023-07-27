package com.tafreshiali.composecanvasplayground.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tafreshiali.composecanvasplayground.ui.theme.Pink40
import com.tafreshiali.composecanvasplayground.ui.theme.Purple40
import com.tafreshiali.composecanvasplayground.ui.theme.White
import com.tafreshiali.composecanvasplayground.utils.convertFromRadiansToDegrees
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
@Composable
fun DraggableCircularSliderComponent(
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    minValue: Int = 0,
    maxValue: Int = 100,
    circleRadius: Float,
    onPositionChange: (Int) -> Unit
) {

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var positionValue by remember {
        mutableStateOf(initialValue)
    }

    var changedAngle by remember {
        mutableStateOf(0f)
    }

    var dragStartedAngle by remember {
        mutableStateOf(0f)
    }

    var oldPositionValue by remember {
        mutableStateOf(initialValue)
    }

    Box(modifier = modifier) {

        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { offset: Offset ->
                        dragStartedAngle =
                            -atan2(
                                x = circleCenter.y - offset.y,
                                y = circleCenter.x - offset.x
                            ).convertFromRadiansToDegrees()
                        dragStartedAngle = (dragStartedAngle + 180f).mod(360f)
                    },
                    onDrag = { change, _ ->
                        var touchAngle = -atan2(
                            x = circleCenter.y - change.position.y,
                            y = circleCenter.x - change.position.x
                        ) * (180f / PI).toFloat()
                        touchAngle = (touchAngle + 180f).mod(360f)

                        val currentAngle = oldPositionValue * 360f / (maxValue - minValue)
                        changedAngle = touchAngle - currentAngle

                        val lowerThreshold = currentAngle - (360f / (maxValue - minValue) * 5)
                        val higherThreshold = currentAngle + (360f / (maxValue - minValue) * 5)

                        if (dragStartedAngle in lowerThreshold..higherThreshold) {
                            positionValue =
                                (oldPositionValue + (changedAngle / (360f / (maxValue - minValue))).roundToInt())
                        }

                    },
                    onDragEnd = {
                        oldPositionValue = positionValue
                        onPositionChange(positionValue)
                    }
                )

            }, onDraw = {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val circleThickness = canvasWidth / 25f
            val canvasCenterWidth = canvasWidth / 2f
            val canvasCenterHeight = canvasHeight / 2f
            circleCenter = Offset(x = canvasCenterWidth, y = canvasCenterHeight)

            //Inner Circle
            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(alpha = 0.45f),
                        secondaryColor.copy(alpha = 0.15f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )


            // Outer Circle
            drawCircle(
                style = Stroke(width = circleThickness),
                color = secondaryColor.copy(alpha = 0.4f),
                radius = circleRadius,
                center = circleCenter
            )


            //Outer Draggable Progressbar
            val outerDraggableProgressbarRadiusSize = circleRadius * 2

            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f / maxValue) * positionValue.toFloat(),
                style = Stroke(width = circleThickness, cap = StrokeCap.Round),
                useCenter = false,
                size = Size(
                    width = outerDraggableProgressbarRadiusSize,
                    height = outerDraggableProgressbarRadiusSize
                ),
                topLeft = Offset(
                    x = (canvasWidth - circleRadius * 2f) / 2f,
                    y = (canvasHeight - circleRadius * 2f) / 2f
                )
            )

            //Outer Indicator

            val outerIndicatorsRadius = circleRadius + circleThickness / 1.8f

            val gapBetweenIndicatorsAndOuterCircle = 15f

            val indicatorsCount = (0..(maxValue - minValue))

            indicatorsCount.forEach { index ->
                val indicatorColor =
                    if (index < positionValue - minValue) primaryColor else primaryColor.copy(
                        alpha = 0.3f
                    )
                //TODO DOUBLE CHECK THIS LINE

                val angleInRadian =
                    calculateAngleInDegrees(index, maxValue, minValue) * PI / 180f + PI / 2f


                val xGapAdjustment = -sin(
                    calculateAngleInDegrees(
                        index,
                        maxValue,
                        minValue
                    ) * PI / 180f
                ) * gapBetweenIndicatorsAndOuterCircle


                val yGapAdjustment = cos(
                    calculateAngleInDegrees(
                        index,
                        maxValue,
                        minValue
                    ) * PI / 180f
                ) * gapBetweenIndicatorsAndOuterCircle


                val startOffset = Offset(
                    x = (outerIndicatorsRadius * cos(angleInRadian) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerIndicatorsRadius * sin(angleInRadian) + circleCenter.y + yGapAdjustment).toFloat()
                )

                val endOffset = Offset(
                    x = (outerIndicatorsRadius * cos(angleInRadian) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerIndicatorsRadius * sin(angleInRadian) + circleThickness + circleCenter.y + yGapAdjustment).toFloat()
                )

                rotate(
                    degrees = calculateAngleInDegrees(index, maxValue, minValue),
                    pivot = startOffset
                ) {
                    drawLine(
                        color = indicatorColor,
                        start = startOffset,
                        end = endOffset,
                        strokeWidth = 2.dp.toPx()
                    )
                }
            }

            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText("$positionValue %",
                        circleCenter.x,
                        circleCenter.y + (38.sp.toPx()) / 2f,
                        Paint().apply {
                            textSize = 38.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = White.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }

        })
    }
}

fun calculateAngleInDegrees(index: Int, maxValue: Int, minValue: Int): Float =
    index * 360f / (maxValue - minValue).toFloat()

@Preview
@Composable
fun DraggableCircularSliderComponentPreview() {
    DraggableCircularSliderComponent(
        modifier = Modifier
            .size(250.dp)
            .background(Color.Black),
        initialValue = 60,
        primaryColor = Purple40,
        secondaryColor = Pink40,
        circleRadius = 230f,
        onPositionChange = {

        }
    )
}
