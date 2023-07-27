package com.tafreshiali.composecanvasplayground.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tafreshiali.composecanvasplayground.utils.convertFromOffsetToDegrees
import com.tafreshiali.composecanvasplayground.utils.detectQuarterBasedOnAngleInDegrees

@Composable
fun BasicDraggableComponent() {
    var touchedAngle by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 15.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        InfoContainer(touchedAngle = touchedAngle)

        DraggableComponent(
            modifier = Modifier
                .size(300.dp)
                .clip(shape = CircleShape)
                .background(color = Color.LightGray),
            onTouchedChangeInDegree = { touchedAngleInDegrees ->
                touchedAngle = touchedAngleInDegrees
            }
        )
    }
}

@Composable
private fun ColumnScope.InfoContainer(touchedAngle: Int) {
    Text(text = "Touched Angle Is $touchedAngle")
    Text(
        text = "user touched the ${detectQuarterBasedOnAngleInDegrees(angleInDegrees = touchedAngle)} quarter"
    )
}

@Composable
private fun DraggableComponent(
    modifier: Modifier = Modifier,
    onTouchedChangeInDegree: (Int) -> Unit
) {
    var touchedOffset by remember {
        mutableStateOf(Offset.Unspecified)
    }

    var canvasCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Canvas(
        modifier = modifier
            .pointerInput(true) {
                detectTapGestures(
                    onPress = {
                        touchedOffset = it
                        onTouchedChangeInDegree(
                            touchedOffset.convertFromOffsetToDegrees(
                                centerOffset = canvasCenter
                            )
                        )
                        awaitRelease()

                        touchedOffset = Offset.Unspecified
                        onTouchedChangeInDegree(0)

                    }

                )
            }
            .pointerInput(true) {
                detectDragGestures(
                    onDrag = { change: PointerInputChange, dragAmount: Offset ->
                        touchedOffset = change.position
                        onTouchedChangeInDegree(
                            touchedOffset.convertFromOffsetToDegrees(
                                centerOffset = canvasCenter
                            )
                        )
                    },
                    onDragStart = { offset: Offset ->


                    },
                    onDragEnd = {
                        touchedOffset = Offset.Unspecified
                        onTouchedChangeInDegree(0)
                    }
                )
            },
        onDraw = {
            canvasCenter = center

            mainXAndYAxis()

            if (touchedOffset != Offset.Unspecified) {
                userTouchedPoint(touchedOffset = touchedOffset)
                highlightThetaAngle(
                    touchedAngleIndDegrees = touchedOffset.convertFromOffsetToDegrees(
                        centerOffset = center
                    ).toFloat()
                )
            }

        })
}

private fun DrawScope.mainXAndYAxis() {
    //x-axis line
    drawLine(
        color = Color.Black,
        start = Offset(x = center.x, y = Offset.Zero.y),
        end = Offset(x = center.x, y = size.height),
        strokeWidth = 1.dp.toPx()
    )

    //y-axis line
    drawLine(
        color = Color.Black,
        start = Offset(x = Offset.Zero.x, y = center.y),
        end = Offset(x = size.width, y = center.y),
        strokeWidth = 1.dp.toPx()
    )
}

private fun DrawScope.userTouchedPoint(touchedOffset: Offset) {
    //touch point
    drawCircle(
        color = Color.Black,
        radius = 30f,
        center = touchedOffset
    )

    //line from touch point center to center of the canvas
    drawLine(
        color = Color.Red,
        start = touchedOffset,
        end = center,
        strokeWidth = 1.2.dp.toPx()
    )

    //line from touch point center to the x-axis
    drawLine(
        color = Color.Black,
        start = touchedOffset,
        end = Offset(x = touchedOffset.x, y = center.y),
        strokeWidth = 1.2.dp.toPx()
    )
}

private fun DrawScope.highlightThetaAngle(touchedAngleIndDegrees: Float) {
    val sizeArc = Size(width = 25.dp.toPx(), height = 25.dp.toPx())
    val theta =
        calculateThetaAnglesForCircleQuarters(touchedAngleIndDegrees = touchedAngleIndDegrees)
    drawArc(

        color = Color.Blue,

        startAngle = theta.startAngle,

        sweepAngle = theta.sweepAngle,

        useCenter = false,

        topLeft = Offset(
            x = (size.width - sizeArc.width) / 2f,
            y = (size.height - sizeArc.height) / 2f
        ),

        size = sizeArc,

        style = Stroke(width = 2.dp.toPx())
    )
}

private data class Theta(val startAngle: Float = 0f, val sweepAngle: Float = 0f)

// Calculate the start and sweep angles for the arc based on the quadrant
private fun calculateThetaAnglesForCircleQuarters(touchedAngleIndDegrees: Float): Theta {
    var theta = Theta()

    when {
        touchedAngleIndDegrees >= 0 && touchedAngleIndDegrees < 90 -> {
            theta = theta.copy(startAngle = 0f, sweepAngle = -touchedAngleIndDegrees)
        }

        touchedAngleIndDegrees >= 90 && touchedAngleIndDegrees < 180 -> {
            theta = theta.copy(startAngle = 180f, sweepAngle = 180f - touchedAngleIndDegrees)
        }

        touchedAngleIndDegrees >= 180 && touchedAngleIndDegrees < 270 -> {
            theta = theta.copy(startAngle = 180f, sweepAngle = -(touchedAngleIndDegrees - 180f))
        }

        else -> {
            theta = theta.copy(startAngle = 0f, sweepAngle = 360f - touchedAngleIndDegrees)
        }
    }
    return theta
}

@Preview
@Composable
fun BasicDraggableComponentPreview() {
    BasicDraggableComponent()
}