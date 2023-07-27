package com.tafreshiali.composecanvasplayground.utils

import androidx.compose.ui.geometry.Offset
import kotlin.math.PI
import kotlin.math.atan2

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