package com.tafreshiali.composecanvasplayground.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextLayoutResult
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun Float.convertFromRadiansToDegrees(): Float = this * (180f / PI).toFloat()

fun Offset.convertFromOffsetToRadians(centerOffset: Offset): Float =
    -atan2(x = centerOffset.x - this.x, y = centerOffset.y - this.y)

//Calculate Angles in Degrees like real world Cartesian coordinates
fun Offset.convertFromOffsetToDegrees(centerOffset: Offset): Int {
    val angleDegrees = Math.toDegrees(convertFromOffsetToRadians(centerOffset).toDouble()).toInt()
    val angleFromRight = (angleDegrees + 180) % 360
    return if (angleFromRight < 0) angleFromRight + 360 else angleFromRight
}

fun detectQuarterBasedOnOffset(touchedOffset: Offset, centerOffset: Offset): Int {
    // Determine the quarter based on the angle in degrees
    return when (touchedOffset.convertFromOffsetToDegrees(centerOffset = centerOffset)) {
        in 0..89 -> 1
        in 90..179 -> 2
        in 180..269 -> 3
        else -> 4
    }
}

fun detectQuarterBasedOnAngleInDegrees(angleInDegrees: Int): Int {
    // Determine the quarter based on the angle in degrees
    return when (angleInDegrees) {
        in 0..89 -> 1
        in 90..179 -> 2
        in 180..269 -> 3
        else -> 4
    }
}

fun TextLayoutResult.calculateVerticalCenterOfAText(): Float {
    // Calculate the line indices of the first and last lines of the text
    val firstLineIndex = 0
    val lastLineIndex = lineCount - 1

    // Calculate the top and bottom y coordinates of the first and last lines
    val firstLineTop = getLineTop(lineIndex = firstLineIndex)
    val lastLineBottom = getLineBottom(lineIndex = lastLineIndex)

    // Calculate the vertical center of the text based on the first and last lines
    return (firstLineTop + lastLineBottom) / 2f
}

fun TextLayoutResult.calculateHorizontalCenterOfAText(): Float {
    // Calculate the line indices of the first and last lines of the text
    val firstLineIndex = 0
    val lastLineIndex = lineCount - 1

    // Calculate the leftmost and rightmost x coordinates of the first line
    val firstLineLeft = getLineLeft(firstLineIndex)
    val firstLineRight = getLineRight(firstLineIndex)

    // Calculate the leftmost and rightmost x coordinates of the last line
    val lastLineLeft = getLineLeft(lastLineIndex)
    val lastLineRight = getLineRight(lastLineIndex)

    // Calculate the horizontal center of the text based on the first and last lines
    return (firstLineLeft + firstLineRight + lastLineLeft + lastLineRight) / 4f
}

fun Offset.calculateAnOffsetOnCircle(radius: Float, thetaDegrees: Int): Offset {
    val thetaLimited = thetaDegrees % 360.0
    val thetaRadians = Math.toRadians(thetaLimited)
    val x = x + radius * cos(thetaRadians)
    val y = y + radius * sin(thetaRadians)
    return Offset(x.toFloat(), y.toFloat())
}

fun findCenterAngle(startAngle: Float, sweepAngle: Float): Float = startAngle + (sweepAngle / 2)

fun divideCircleAnglesInToParts(index: Int, divideCount: Float): Float =
    index * calculateAnglePerValue(divideCount = divideCount)

fun calculateAnglePerValue(divideCount: Float): Float =
    Constance.COMPLETE_CIRCLE_DEGREE / divideCount

// Function to calculate the distance between two points
fun Offset.calculateDistanceFrom(offset: Offset): Float {
    val dx = this.x - offset.x
    val dy = this.y - offset.y
    return sqrt(dx * dx + dy * dy)
}

fun DrawScope.calculateTheCenterOffsetForTheThetaArc(arcSize: Size): Offset =
    Offset(
        x = (size.width - arcSize.width) / 2f,
        y = (size.height - arcSize.height) / 2f
    )
